package com.holtebu.bosetterne.service.helloworld;

import org.atmosphere.cpr.AtmosphereServlet;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.holtebu.bosetterne.service.helloworld.health.TemplateHealthCheck;
import com.holtebu.bosetterne.service.helloworld.resources.HelloWorldResource;
import com.holtebu.bosetterne.service.helloworld.resources.MyResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;
//import com.yammer.dropwizard.config.FilterBuilder;

//import com.yammer.dropwizard.views.ViewBundle;

//import org.eclipse.jetty.servlets.CrossOriginFilter;

public class HelloWorldService extends Service<HelloWorldConfiguration> {

	private HelloWorldService () {
		
	}
	
	public static void main(String[] args) throws Exception {
		
        new HelloWorldService().run(args);
    }
	
	private final HibernateBundle<HelloWorldConfiguration> hibernate = new HibernateBundle<HelloWorldConfiguration>(Person.class) {
	    @Override
	    public DatabaseConfiguration getDatabaseConfiguration(HelloWorldConfiguration configuration) {
	        return configuration.getDatabaseConfiguration();
	    }
	};

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.setName("hello-world");
        bootstrap.addBundle(new AssetsBundle("/WebContent/backup", "/"));
        bootstrap.addBundle(hibernate);
        mvn install:install-file -Dfile=<path-to-file> -DgroupId=<group-id> -DartifactId=<artifact-id> -Dversion=<version> -Dpackaging=<packaging>
        //Noe customgreier
        //bootstrap.addCommand(new RenderCommand());

        //For html views
        //bootstrap.addBundle(new ViewBundle());
    }
    
    void initializeAtmosphere(HelloWorldConfiguration configuration, Environment environment) {
        //FilterBuilder fconfig = environment.addFilter(CrossOriginFilter.class, "/chat");
        //fconfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");

        AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
        atmosphereServlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "com.holtebu.bosetterne.service.helloworld.resources.atmosphere");
        atmosphereServlet.framework().addInitParameter("org.atmosphere.websocket.messageContentType", "application/json");
        //atmosphereServlet.framework().addInitParameter("org.atmosphere.cpr.broadcastFilterClasses", "com.example.helloworld.filters.BadWordFilter");
        environment.addServlet(atmosphereServlet, "/service/atmos/*");
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) {
        //final String template = configuration.getTemplate();
        //final String defaultName = configuration.getDefaultName();
        Injector helloWorldInjector = createInjector(configuration);
        BosetterneModule bosetterneModule = helloWorldInjector.getInstance(BosetterneModule.class);
        Injector bosetterneInjector = Guice.createInjector(bosetterneModule);
        
        environment.addResource(helloWorldInjector.getInstance(HelloWorldResource.class));
        environment.addHealthCheck(helloWorldInjector.getInstance(TemplateHealthCheck.class));
        environment.addResource(bosetterneInjector.getInstance(MyResource.class));
        //TODO: Atmosphere resources
        //initializeAtmosphere(configuration, environment);
    }
    
    private Injector createInjector(final HelloWorldConfiguration conf) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            	bind(HelloWorldConfiguration.class).toInstance(conf);
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
