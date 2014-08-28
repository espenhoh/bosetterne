package com.holtebu.bosetterne.service.inject;

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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

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
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.skife.jdbi.v2.DBI;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
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
import com.holtebu.bosetterne.service.resources.HelloWorldResource;
import com.holtebu.bosetterne.service.resources.OAuthAccessTokenResource;
import com.holtebu.bosetterne.service.resources.lobby.LobbyResource;
import com.holtebu.bosetterne.service.resources.lobby.OAuthAuthorizeResource;

public class BosetterneServiceBinder extends AbstractBinder{
	private final DBIFactory dbiFactory;
	
	private BosetterneConfiguration configuration;
	private Environment environment;
	private DBI jdbi;
	private DAOFactory daoFactory;

	public BosetterneServiceBinder(final DBIFactory factory) {
		dbiFactory = factory;
	}
	
	public void setUpEnv(BosetterneConfiguration configuration, Environment environment) {
		this.configuration = configuration;
		this.environment = environment;
	}
	
	public void setUpDao(DBI jdbi){
		this.jdbi = jdbi;
	}

	@Override
	protected void configure() {
		bind(HelloWorldResource.class).to(HelloWorldResource.class).in(Singleton.class);		
		bindFactory(OAuthFactoryFactory.class).to(OAuthFactory.class);//.in(Singleton.class);
		bind(OAuthAccessTokenResource.class).to(OAuthAccessTokenResource.class).in(Singleton.class);
		bind(OAuthAuthorizeResource.class).to(OAuthAuthorizeResource.class).in(Singleton.class);
		bind(LobbyResource.class).to(LobbyResource.class).in(Singleton.class);
		
		
		//DAO binding
		bind(jdbi).to(DBI.class);
		bindFactory(DAOFactory.LobbyDAOFactory.class).to(LobbyDAO.class);
		
		
		bind("protected-resources").to(String.class).named("realm");
		bind(BosetterneAuthenticator.class).to(new TypeLiteral<Authenticator<String,Spiller>>() {});
		
		bind(configuration).to(BosetterneConfiguration.class);
		bind(environment).to(Environment.class);
		
		bind(AtomicLong.class).to(AtomicLong.class);
		 
		bind(HashMap.class).to(new TypeLiteral<Map<String, Spiller>>(){}).named("tokens");
		bind(HashMap.class).to(new TypeLiteral<Map<String, Legitimasjon>>(){}).named("codes");
		
		bind(configuration.getOauth2()).to(OAuth2Cred.class);
		bind(PolettlagerIMinne.class).to(new TypeLiteral<Polettlager<AccessToken, Spiller, Legitimasjon, String>>(){}).in(Singleton.class);;
		
		//Cache til JDBILobbyservice
		bindFactory(LobbyCacheFactory.class).to(new TypeLiteral<LoadingCache<String, Optional<Spiller>>>(){});
		bind(JDBILobbyService.class).to(new TypeLiteral<LobbyService<Optional<Spiller>, BasicCredentials>>(){}).in(Singleton.class);
		
        bind("ingenting").to(String.class).named("getit");
        
        //bind(Integer.class).annotatedWith(Names.named("antallSpill")).toInstance(INIT_ANTALL_SPILL);
        //bind(String.class).annotatedWith(Realm.class).toInstance(REALM);
        //bind(DBI.class).toInstance(jdbi); //Trengs denne?
        
        //Bindtemplates
        //bind(String.class).annotatedWith(LoggInnTemplate.class).toInstance("/WebContent/lobby/login.mustache");
        //bind(String.class).annotatedWith(HjemTemplate.class).toInstance("/WebContent/lobby/hjem.mustache");
        
        //bind(InjectableOAuthProvider.class).to(new TypeLiteral<InjectableOAuthProvider<Spiller>>(){});
        //bind(new TypeLiteral<Polettlager<AccessToken, Spiller, Legitimasjon, String>>(){}).to(PolettlagerIMinne.class).in(Scopes.SINGLETON);
        //bind(new TypeLiteral<LobbyService<Optional<Spiller>, BasicCredentials>>(){}).to(JDBILobbyService.class).in(Scopes.SINGLETON);
        //bind(new TypeLiteral<Authenticator<String, Spiller>>(){}).to(BosetterneAuthenticator.class).in(Scopes.SINGLETON);
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
