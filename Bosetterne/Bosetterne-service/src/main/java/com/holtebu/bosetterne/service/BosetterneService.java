package com.holtebu.bosetterne.service;


import org.skife.jdbi.v2.DBI;

import com.google.common.base.Optional;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
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
import com.holtebu.bosetterne.service.health.TemplateHealthCheck;
import com.holtebu.bosetterne.service.resources.HelloWorldResource;
import com.holtebu.bosetterne.service.resources.LobbyResource;
import com.holtebu.bosetterne.service.resources.MyResource;
import com.holtebu.bosetterne.service.resources.OAuthAccessTokenResource;
import com.holtebu.bosetterne.service.resources.OAuthAuthorizeResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.auth.basic.BasicCredentials;
import com.yammer.dropwizard.auth.oauth.OAuthProvider;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import com.yammer.dropwizard.jdbi.bundles.DBIExceptionsBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import com.yammer.dropwizard.config.FilterBuilder;

//import com.yammer.dropwizard.views.ViewBundle;

//import org.eclipse.jetty.servlets.CrossOriginFilter;

public class BosetterneService extends Service<BosetterneConfiguration> {
	
    private final static Logger logger = LoggerFactory.getLogger("BosetterneService.class");

	private BosetterneService () {
		
	}
	
	public static void main(String[] args) throws Exception {
		
        new BosetterneService().run(args);
    }


    @Override
    public void initialize(Bootstrap<BosetterneConfiguration> bootstrap) {
        bootstrap.setName("Bosetterne - yay");
        bootstrap.addBundle(new AssetsBundle("/WebContent/", "/"));
        bootstrap.addBundle(new DBIExceptionsBundle());
    }
    
    void initializeAtmosphere(BosetterneConfiguration configuration, Environment environment) {
        //FilterBuilder fconfig = environment.addFilter(CrossOriginFilter.class, "/chat");
        //fconfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");

        //AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
        //atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "com.holtebu.bosetterne.service.helloworld.resources.atmosphere");
        //atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.messageContentType", "application/json");
        //atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.broadcastFilterClasses", "com.example.helloworld.filters.BadWordFilter");
        //environment.addServlet(atmosphereServlet, "/service/atmos/*");
    }

    @Override
    public void run(BosetterneConfiguration configuration,
                    Environment environment) {
    	
    	//Dependency injectors
    	logger.info("1/5 Setter opp Guice injector");
        Injector bosetterneInjector = Guice.createInjector(new BosetterneModule(configuration, environment));
        

        
        //Authentication
        logger.info("2/5 Setter opp autentisering og autorisering med polettlager i minnet.");
        environment.addProvider(bosetterneInjector.getInstance(InjectableOAuthProvider.class));
        environment.addResource(bosetterneInjector.getInstance(OAuthAccessTokenResource.class));
        environment.addResource(bosetterneInjector.getInstance(OAuthAuthorizeResource.class));
        //environment.addProvider(new OAuthProvider<Spiller>(new BosetterneAuthenticator(), "SUPER SECRET STUFF"));
        
        //Uten injector
        //Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore = bosetterneInjector.getInstance(PolettlagerIMinne.class);
        //LobbyService<Optional<Spiller>, BasicCredentials> lobbyService = bosetterneInjector.getInstance(JDBILobbyService.class);
        //environment.addProvider(new OAuthProvider<Spiller>(new BosetterneAuthenticator(tokenStore), "protected-resources"));
        //environment.addResource(new OAuthAccessTokenResource(tokenStore));
        //environment.addResource(new OAuthAuthorizeResource(tokenStore, lobbyService));
        
        
        //Resources
        logger.info("3/5 Legger til standard resources");
        environment.addResource(bosetterneInjector.getInstance(LobbyResource.class));
        environment.addResource(bosetterneInjector.getInstance(HelloWorldResource.class));
        environment.addResource(bosetterneInjector.getInstance(MyResource.class));
        
        //Health checks
        logger.info("4/5 Legger til HealthChecks");
        environment.addHealthCheck(bosetterneInjector.getInstance(TemplateHealthCheck.class));
        
        //Filter
        //environment.addFilter(bosetterneInjector.getInstance(Sikkerhetsfilter.class), "/myapp/*");
        
        //TODO: Atmosphere resources
        logger.info("5/5 TODO: Atmosphere resources");
        //initializeAtmosphere(configuration, environment);
    }
}
/*

public class HelloWorldService extends Service<HelloWorldConfiguration> {

    
    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.setName("hello-world");
        bootstrap.addCommand(new RenderCommand());
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>() {
            @Override
            public DatabaseConfiguration getDatabaseConfiguration(HelloWorldConfiguration configuration) {
                return configuration.getDatabaseConfiguration();
            }
        });

        bootstrap.addBundle(new ViewBundle());
    }

    void initializeAtmosphere(HelloWorldConfiguration configuration, Environment environment) {
        FilterBuilder fconfig = environment.addFilter(CrossOriginFilter.class, "/chat");
        fconfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");

        AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
        atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "com.example.helloworld.resources.atmosphere");
        atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.messageContentType", "application/json");
        atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.broadcastFilterClasses", "com.example.helloworld.filters.BadWordFilter");
        environment.addServlet(atmosphereServlet, "/chat/*");
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {

        final PersonDAO dao = new PersonDAO(hibernateBundle.getSessionFactory());

        environment.addProvider(new BasicAuthProvider<User>(new ExampleAuthenticator(), "SUPER SECRET STUFF"));

        final Template template = configuration.buildTemplate();

        environment.addHealthCheck(new TemplateHealthCheck(template));
        environment.addResource(new HelloWorldResource(template));
        environment.addResource(new ViewResource());
        environment.addResource(new ProtectedResource());
        environment.addResource(new PeopleResource(dao));
        environment.addResource(new PersonResource(dao));

        initializeAtmosphere(configuration, environment);
    }
}*/
