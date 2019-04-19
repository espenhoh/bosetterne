package com.holtebu.brettspill.service;


import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.health.TemplateHealthCheck;
import com.holtebu.brettspill.service.inject.BosetterneServiceBinder;
import com.holtebu.brettspill.service.inject.ResourceBundleResolver;
import com.holtebu.brettspill.service.inject.StartupBinder;
import com.holtebu.brettspill.service.resources.games.BosetterneResource;
import com.holtebu.brettspill.service.resources.HelloWorldResource;
import com.holtebu.brettspill.service.resources.OAuthAccessTokenResource;
import com.holtebu.brettspill.service.resources.lobby.LobbyResource;
import com.holtebu.brettspill.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.brettspill.service.resources.lobby.RegistrerResource;
import com.holtebu.brettspill.service.views.mustachehack.MustacheViewBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;


//import com.yammer.dropwizard.config.FilterBuilder;

//import com.yammer.dropwizard.views.ViewBundle;

//import org.eclipse.jetty.servlets.CrossOriginFilter;

public class BosetterneService extends Application<BosetterneConfiguration> {
	
    private final static Logger logger = LoggerFactory.getLogger("BosetterneService.class");
    private final BosetterneServiceBinder binder;

	@Inject
	public BosetterneService(BosetterneServiceBinder binder) {
		this.binder = binder;
	}

	public static void main(String[] args) throws Exception {
        ServiceLocator brettspillLocator = ServiceLocatorUtilities.bind(UUID.randomUUID().toString(), new StartupBinder());
        BosetterneService service = brettspillLocator.getService(BosetterneService.class);
        service.run(args);
    }


    @Override
    public void initialize(Bootstrap<BosetterneConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/WebContent/app/", "/app/"));
        bootstrap.addBundle(new DBIExceptionsBundle());
        bootstrap.addBundle(new MustacheViewBundle<BosetterneConfiguration>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(BosetterneConfiguration config) {
                return config.getViewRendererConfiguration();
            }
        });
    }
    
    @Override
    public String getName() {
        return "Bosetterne - yay!";
    }
    
    //void initializeAtmosphere(BosetterneConfiguration configuration, Environment environment) {
        //FilterBuilder fconfig = environment.addFilter(CrossOriginFilter.class, "/chat");
        //fconfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");

        //AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
        //atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "com.holtebu.bosetterne.service.helloworld.resources.atmosphere");
        //atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.messageContentType", "application/json");
        //atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.broadcastFilterClasses", "com.example.helloworld.filters.BadWordFilter");
        //environment.addServlet(atmosphereServlet, "/service/atmos/*");
    //}

    @Override
    public void run(BosetterneConfiguration configuration, Environment environment) {
    	logger.info("0/5 Henter opp Jersey");
    	JerseyEnvironment jersey = environment.jersey();

    	//Dependency injectors
    	logger.info("1/5 Setter opp hk2 injector");
    	binder.setUpEnv(configuration, environment);
    	jersey.register(binder);
    	jersey.register(new ResourceBundleResolver.Binder());

    	
    	
        //Authentication
        logger.info("2/5 Setter opp autentisering og autorisering med polettlager i minnet.");

        jersey.register(new AuthValueFactoryProvider.Binder<>(Spiller.class));
        jersey.register(RolesAllowedDynamicFeature.class);
        jersey.register(new AuthDynamicFeature(binder.filter()));

        //BosetterneOAuthFilter filter = new BosetterneOAuthFilter(binder.getTokenStore());
        //jersey.register(filter.getFilter());
        jersey.register(OAuthAccessTokenResource.class);
        jersey.register(OAuthAuthorizeResource.class);

        
        //Resources
        logger.info("3/5 Legger til standard resources");
        jersey.register(LobbyResource.class);
        jersey.register(RegistrerResource.class);
        jersey.register(HelloWorldResource.class);
        jersey.register(BosetterneResource.class);

        //Health checks
        logger.info("4/5 Legger til HealthChecks");
        environment.healthChecks().register("template", new TemplateHealthCheck(configuration));
        
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
