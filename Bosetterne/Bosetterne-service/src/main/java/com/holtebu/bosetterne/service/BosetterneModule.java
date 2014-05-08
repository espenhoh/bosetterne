package com.holtebu.bosetterne.service;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.skife.jdbi.v2.DBI;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.auth.BosetterneAuthenticator;
import com.holtebu.bosetterne.service.auth.InjectableOAuthProvider;
import com.holtebu.bosetterne.service.auth.JDBILobbyService;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.inject.names.HjemTemplate;
import com.holtebu.bosetterne.service.inject.names.LoggInnTemplate;
import com.holtebu.bosetterne.service.inject.names.Realm;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi.DBIFactory;

public class BosetterneModule extends AbstractModule {
	private final BosetterneConfiguration configuration;
	//private final Environment environment;
	private final Integer INIT_ANTALL_SPILL = 20;
	private final DBI jdbi;
	
	private final String basePath = "/WebContent/";
    
	public BosetterneModule(final BosetterneConfiguration configuration, Environment environment) {
		this.configuration = configuration;
		//this.environment = environment;
		this.jdbi = getJDBI(environment);
	}
	
	public BosetterneModule(final BosetterneConfiguration configuration, DBI dbi) {
		this.configuration = configuration;
		this.jdbi = dbi;
	}
	
	@Override
    protected void configure() {
    	bind(BosetterneConfiguration.class).toInstance(configuration);
    	//bind(Environment.class).toInstance(environment);
    	bind(OAuth2Cred.class).toInstance(configuration.getOauth2());
		bind(new TypeLiteral<Map<String, Spiller>>(){}).to(new TypeLiteral<HashMap<String, Spiller>>(){});
		bind(new TypeLiteral<Map<String, Legitimasjon>>(){}).to(new TypeLiteral<HashMap<String, Legitimasjon>>(){});
        bind(String.class).annotatedWith(Names.named("getit")).toInstance("ingenting");
        bind(Integer.class).annotatedWith(Names.named("antallSpill")).toInstance(INIT_ANTALL_SPILL);
        bind(String.class).annotatedWith(Realm.class).toInstance("protected-resources");
        //bind(DBI.class).toInstance(jdbi); //Trengs denne?
        
        //Bindtemplates
        //bind(String.class).annotatedWith(LoggInnTemplate.class).toInstance("/WebContent/lobby/login.mustache");
        //bind(String.class).annotatedWith(HjemTemplate.class).toInstance("/WebContent/lobby/hjem.mustache");
        
        bind(InjectableOAuthProvider.class).to(new TypeLiteral<InjectableOAuthProvider<Spiller>>(){});
        bind(new TypeLiteral<Polettlager<AccessToken, Spiller, Legitimasjon, String>>(){}).to(PolettlagerIMinne.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<LobbyService<Optional<Spiller>, BasicCredentials>>(){}).to(JDBILobbyService.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<Authenticator<String, Spiller>>(){}).to(BosetterneAuthenticator.class).in(Scopes.SINGLETON);
    }
    
	/**
	 * @return the singel JDBI instance required
	 */
    private DBI getJDBI(Environment environment){
    	final DBIFactory factory = new DBIFactory();
    	DBI jdbi = null;
        try {
        	jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mySQL");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
        return jdbi;
    }
    
	@Provides
	LobbyDAO provideSpillerDAO () {
        return jdbi.onDemand(LobbyDAO.class);
	}
	
	@Provides @Named("spillerCache")
	LoadingCache<String, Optional<Spiller>> provideSpillerCache(){
		final LobbyDAO dao = provideSpillerDAO();
		CacheLoader<String, Optional<Spiller>> loader = new CacheLoader<String, Optional<Spiller>> () {
			  public Optional<Spiller> load(String key) throws Exception {
				  return Optional.fromNullable(dao.finnSpillerVedNavn(key));
			  }
		};

		return CacheBuilder.newBuilder()
	       .maximumSize(1000)
	       .expireAfterWrite(10, TimeUnit.MINUTES)
	       .build(loader);
	}
}
