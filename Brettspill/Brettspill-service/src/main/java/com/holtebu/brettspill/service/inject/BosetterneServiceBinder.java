package com.holtebu.brettspill.service.inject;

import com.google.common.base.Optional;
import com.google.common.cache.LoadingCache;
import com.holtebu.brettspill.api.Bosetterne;
import com.holtebu.brettspill.api.lobby.Spill;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.BosetterneConfiguration;
import com.holtebu.brettspill.service.OAuth2Cred;
import com.holtebu.brettspill.service.auth.BosetterneAuthenticator;
import com.holtebu.brettspill.service.auth.BosetterneAuthorizer;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.brettspill.service.core.AccessToken;
import com.holtebu.brettspill.service.core.Legitimasjon;
import com.holtebu.brettspill.service.core.dao.LobbyDAO;
import com.holtebu.brettspill.service.resources.BosetterneResource;
import com.holtebu.brettspill.service.resources.HelloWorldResource;
import com.holtebu.brettspill.service.resources.OAuthAccessTokenResource;
import com.holtebu.brettspill.service.resources.lobby.LobbyResource;
import com.holtebu.brettspill.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.brettspill.service.resources.lobby.RegistrerResource;
import com.holtebu.brettspill.service.auth.JDBILobbyService;
import com.holtebu.brettspill.service.auth.LobbyService;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

public class BosetterneServiceBinder extends AbstractBinder{
	private final DBIFactory dbiFactory;
	private final Integer INIT_ANTALL_SPILL = 20;
	private BosetterneConfiguration configuration;

	private Environment environment;
	private DBI jdbi;
	private DAOFactory daoFactory;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;

	@Inject
	public BosetterneServiceBinder(final DBIFactory factory) {
		dbiFactory = factory;
	}

    BosetterneServiceBinder(DBIFactory dbiFactory, BosetterneConfiguration configuration, Environment environment) {
        this(dbiFactory);
        setUpEnv(configuration, environment);
    }

    public void setUpEnv(BosetterneConfiguration configuration, Environment environment) {
		this.configuration = configuration;
		this.environment = environment;
		this.jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "mySQL");
		this.daoFactory = new DAOFactory(jdbi);
		//tokenStore = new PolettlagerIMinne(new HashMap<String, Spiller>(), new HashMap<String, Legitimasjon>(), configuration.getOauth2());
		//authFactory = new OAuthFactory<Spiller>(new BosetterneAuthenticator(tokenStore), "protected", Spiller.class);
	}

	@Override
	protected void configure() {
        bindClasses();

		
		//bindResourceProviders();
		bind(ResourceBundle.getBundle("bosetterne")).to(ResourceBundle.class);
		
		
		//DAO bindingK,
		bindFactory(daoFactory.lobbyDAOFactory()).to(LobbyDAO.class);
		
		bind(configuration).to(BosetterneConfiguration.class);

		
		bind(environment).to(Environment.class);
		 
		bind(ConcurrentHashMap.class).to(new TypeLiteral<Map<String, Spiller>>(){}).named("tokens");
		bind(ConcurrentHashMap.class).to(new TypeLiteral<Map<String, Legitimasjon>>(){}).named("codes");
		bind(configuration.getOauth2()).to(OAuth2Cred.class);
		//bind(CopyOnWriteArrayList.class).to(new TypeLiteral<List<Spill>>() {}).named("spillCache");
		bind(ConcurrentSkipListSet.class).to(new TypeLiteral<Set<Spill>>() {}).named("spillCache");
		bind(PolettlagerIMinne.class).to(new TypeLiteral<Polettlager<AccessToken, Spiller, Legitimasjon, String>>(){}).in(Singleton.class);
		
		
		
		//Cache til JDBILobbyservice
		bindFactory(LobbyCacheFactory.class).to(new TypeLiteral<LoadingCache<String, Optional<Spiller>>>(){});
		bind(JDBILobbyService.class).to(new TypeLiteral<LobbyService<Optional<Spiller>, BasicCredentials>>(){}).in(Singleton.class);
		
        bind("ingenting").to(String.class).named("getit");
        
        //Bosetterne binding
        bind(Random.class).to(Random.class);
        bind(INIT_ANTALL_SPILL).to(Integer.class).named("antallSpill");

	}

    private void bindClasses() {
        bind(HelloWorldResource.class).to(HelloWorldResource.class).in(Singleton.class);
        bind(OAuthAccessTokenResource.class).to(OAuthAccessTokenResource.class).in(Singleton.class);
        bind(OAuthAuthorizeResource.class).to(OAuthAuthorizeResource.class).in(Singleton.class);
        bind(LobbyResource.class).to(LobbyResource.class).in(Singleton.class);
        bind(RegistrerResource.class).to(RegistrerResource.class).in(Singleton.class);
        bind(BosetterneResource.class).to(BosetterneResource.class).in(Singleton.class);
        bind(Bosetterne.class).to(Bosetterne.class).in(Singleton.class);
        bind(BosetterneAuthenticator.class).to(BosetterneAuthenticator.class).in(Singleton.class);
        bind(AtomicLong.class).to(AtomicLong.class);
    }

    public OAuthCredentialAuthFilter filter(){
		return new OAuthCredentialAuthFilter.Builder<Spiller>()
                .setAuthenticator(new BosetterneAuthenticator(tokenStore))
                .setRealm("Bosetterne")
                .setPrefix("Bearer")
                .setAuthorizer(new BosetterneAuthorizer())
                .buildAuthFilter();
	}



}
