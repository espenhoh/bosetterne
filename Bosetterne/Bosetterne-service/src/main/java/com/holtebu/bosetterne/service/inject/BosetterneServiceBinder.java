package com.holtebu.bosetterne.service.inject;

import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Provider;
import javax.inject.Singleton;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorState;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.skife.jdbi.v2.DBI;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.holtebu.bosetterne.api.Bosetterne;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.auth.BosetterneAuthenticator;
import com.holtebu.bosetterne.service.auth.JDBILobbyService;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.resources.BosetterneResource;
import com.holtebu.bosetterne.service.resources.HelloWorldResource;
import com.holtebu.bosetterne.service.resources.OAuthAccessTokenResource;
import com.holtebu.bosetterne.service.resources.lobby.LobbyResource;
import com.holtebu.bosetterne.service.resources.lobby.LoggInnResource;
import com.holtebu.bosetterne.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.bosetterne.service.resources.lobby.RegistrerResource;

public class BosetterneServiceBinder extends AbstractBinder{
	private final DBIFactory dbiFactory;
	private final Integer INIT_ANTALL_SPILL = 20;
	
	private BosetterneConfiguration configuration;
	private Environment environment;
	private DBI jdbi;
	private DAOFactory daoFactory;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;
	private OAuthFactory<Spiller> authFactory;

	public BosetterneServiceBinder(final DBIFactory factory) {
		dbiFactory = factory;
	}
	
	public void setUpEnv(BosetterneConfiguration configuration, Environment environment) {
		this.configuration = configuration;
		this.environment = environment;
		tokenStore = new PolettlagerIMinne(new HashMap<String, Spiller>(), new HashMap<String, Legitimasjon>(), configuration.getOauth2());
		authFactory = new OAuthFactory<Spiller>(new BosetterneAuthenticator(tokenStore), "protected", Spiller.class);
	}
	
	public void setUpDao(DBI jdbi){
		this.jdbi = jdbi;
		daoFactory = new DAOFactory(jdbi);
	}

	@Override
	protected void configure() {
		bind(HelloWorldResource.class).to(HelloWorldResource.class).in(Singleton.class);		
		//bindFactory(OAuthFactoryFactory.class).to(OAuthFactory.class);//.in(Singleton.class);
		bind(OAuthAccessTokenResource.class).to(OAuthAccessTokenResource.class).in(Singleton.class);
		bind(OAuthAuthorizeResource.class).to(OAuthAuthorizeResource.class).in(Singleton.class);
		bind(LobbyResource.class).to(LobbyResource.class).in(Singleton.class);
		bind(RegistrerResource.class).to(RegistrerResource.class).in(Singleton.class);
		bind(BosetterneResource.class).to(BosetterneResource.class).in(Singleton.class);
		bind(Bosetterne.class).to(Bosetterne.class).in(Singleton.class);
		
		//bindResourceProviders();
		
		
		
		//DAO binding
		bindFactory(daoFactory.lobbyDAOFactory()).to(LobbyDAO.class);
		
		bind("protected-resources").to(String.class).named("realm");
		//bind(BosetterneAuthenticator.class).to(new TypeLiteral<Authenticator<String,Spiller>>() {});
		
		bind(configuration).to(BosetterneConfiguration.class);
		bind(configuration.getOauth2()).to(OAuth2Cred.class);
		
		bind(environment).to(Environment.class);
		
		bind(AtomicLong.class).to(AtomicLong.class);
		 
		bind(HashMap.class).to(new TypeLiteral<Map<String, Spiller>>(){}).named("tokens");
		bind(HashMap.class).to(new TypeLiteral<Map<String, Legitimasjon>>(){}).named("codes");
		
		
		
		//Tokenstore
		bind(tokenStore).to(new TypeLiteral<Polettlager<AccessToken, Spiller, Legitimasjon, String>>(){});
		
		
		
		//Cache til JDBILobbyservice
		bindFactory(LobbyCacheFactory.class).to(new TypeLiteral<LoadingCache<String, Optional<Spiller>>>(){});
		bind(JDBILobbyService.class).to(new TypeLiteral<LobbyService<Optional<Spiller>, BasicCredentials>>(){}).in(Singleton.class);
		
        bind("ingenting").to(String.class).named("getit");
        
        //Bosetterne binding
        bind(Random.class).to(Random.class);
        bind(INIT_ANTALL_SPILL).to(Integer.class).named("antallSpill");
        
        //Bindtemplates
        //bind(String.class).annotatedWith(LoggInnTemplate.class).toInstance("/WebContent/lobby/login.mustache");
        //bind(String.class).annotatedWith(HjemTemplate.class).toInstance("/WebContent/lobby/hjem.mustache");

	}
	
	private void bindResourceProviders() {
		bind(new Provider<LoggInnResource>() {
			@Override
			public LoggInnResource get() {
				return new LoggInnResource(configuration.getMustacheTemplates().getLoginTemplate());
			}
		}).to(new TypeLiteral<Provider<LoggInnResource>>(){});
		
	}

	public Binder getAuthFactoryBinder(){
		return AuthFactory.binder(authFactory);
	}
	
	/**
	 * @return the single JDBI instance required
	 */
    public DBI buildJDBI(){
    	DBI jdbi = null;
        try {
        	jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "mySQL");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Kunne ikke starte opp databasen!");
		}
        return jdbi;
    }
	

}
