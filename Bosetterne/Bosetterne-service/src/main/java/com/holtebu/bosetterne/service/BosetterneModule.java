package com.holtebu.bosetterne.service;

import org.skife.jdbi.v2.DBI;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;

public class BosetterneModule extends AbstractModule {
	private final BosetterneConfiguration configuration;
	private final Environment environment;
	
	private final Integer INIT_ANTALL_SPILL = 20;
	
	private final DBI jdbi;
    
	@Inject
	public BosetterneModule(final BosetterneConfiguration configuration, Environment environment, DBI jdbi) throws ClassNotFoundException {
		this.configuration = configuration;
		this.environment = environment;
		this.jdbi = jdbi;
	}
	
	@Override
    protected void configure() {
    	bind(BosetterneConfiguration.class).toInstance(configuration);
    	
        bind(String.class).annotatedWith(Names.named("getit")).toInstance("ingenting");
        
        bind(Integer.class).annotatedWith(Names.named("antallSpill")).toInstance(INIT_ANTALL_SPILL);
    }
	
	@Provides
	LobbyDAO provideSpillerDAO () {
        return jdbi.onDemand(LobbyDAO.class);
	}
}
