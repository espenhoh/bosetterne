package com.holtebu.bosetterne.service.bosetterne;

import org.atmosphere.cpr.AtmosphereServlet;
import org.skife.jdbi.v2.DBI;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.holtebu.bosetterne.service.bosetterne.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.bosetterne.health.TemplateHealthCheck;
import com.holtebu.bosetterne.service.bosetterne.resources.HelloWorldResource;
import com.holtebu.bosetterne.service.bosetterne.resources.LobbyResource;
import com.holtebu.bosetterne.service.bosetterne.resources.MyResource;
import com.holtebu.bosetterne.service.bosetterne.auth.BosetterneAuthenticator;
import com.holtebu.bosetterne.service.bosetterne.auth.LobbyAuthenticator;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;
import com.yammer.dropwizard.auth.oauth.OAuthProvider;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.jdbi.DBIFactory;

//import com.yammer.dropwizard.config.FilterBuilder;

//import com.yammer.dropwizard.views.ViewBundle;

//import org.eclipse.jetty.servlets.CrossOriginFilter;

public class BosetterneService extends Service<BosetterneConfiguration> {

	private BosetterneService () {
		
	}
	
	public static void main(String[] args) throws Exception {
		
        new BosetterneService().run(args);
    }
	
//	private final HibernateBundle<BosetterneConfiguration> hibernate = new HibernateBundle<BosetterneConfiguration>(Spiller.class) {
//	    @Override
//	    public DatabaseConfiguration getDatabaseConfiguration(BosetterneConfiguration configuration) {
//	        return configuration.getDatabaseConfiguration();
//	    }
//	};

    @Override
    public void initialize(Bootstrap<BosetterneConfiguration> bootstrap) {
        bootstrap.setName("Bosetterne - yay");
        bootstrap.addBundle(new AssetsBundle("/WebContent/", "/"));
        
        // TODO HIBERNATE Maybe
        //bootstrap.addBundle(hibernate);
        
       
    }
    
    void initializeAtmosphere(BosetterneConfiguration configuration, Environment environment) {
        //FilterBuilder fconfig = environment.addFilter(CrossOriginFilter.class, "/chat");
        //fconfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");

        AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
        atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "com.holtebu.bosetterne.service.helloworld.resources.atmosphere");
        atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.messageContentType", "application/json");
        //atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.broadcastFilterClasses", "com.example.helloworld.filters.BadWordFilter");
        environment.addServlet(atmosphereServlet, "/service/atmos/*");
    }

    @Override
    public void run(BosetterneConfiguration configuration,
                    Environment environment) {
    	
    	//Dependency injectors
        Injector helloWorldInjector = createInjector(configuration, environment);
        BosetterneModule bosetterneModule = helloWorldInjector.getInstance(BosetterneModule.class);
        Injector bosetterneInjector = Guice.createInjector(bosetterneModule);
        
        //Resources
        environment.addResource(helloWorldInjector.getInstance(HelloWorldResource.class));
        
        
        environment.addResource(bosetterneInjector.getInstance(MyResource.class));
        
        //Health checks
        environment.addHealthCheck(helloWorldInjector.getInstance(TemplateHealthCheck.class));
        
        //Authentication
        environment.addProvider(new BasicAuthProvider<Spiller>(new LobbyAuthenticator(bosetterneInjector.getInstance(LobbyDAO.class)),"SUPER SECRET STUFF"));
        //environment.addProvider(new OAuthProvider<Spiller>(new BosetterneAuthenticator(), "SUPER SECRET STUFF"));
        
        //JDBI
        environment.addResource(bosetterneInjector.getInstance(LobbyResource.class));
        
        //TODO: Atmosphere resources
        //initializeAtmosphere(configuration, environment);
    }
    
    private Injector createInjector(final BosetterneConfiguration conf, final Environment environment) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            	bind(BosetterneConfiguration.class).toInstance(conf);
            	bind(Environment.class).toInstance(environment);
            	//bind(SessionFactory.class).toInstance(hibernate.getSessionFactory());
                bind(String.class).annotatedWith(Names.named("getit")).toInstance("ingenting");
            }
        });
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
