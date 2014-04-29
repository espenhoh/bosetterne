package com.holtebu.bosetterne.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;

public class BosetterneModule extends AbstractModule {
	private final BosetterneConfiguration configuration;
	private final Environment environment;
	
	private final Integer INIT_ANTALL_SPILL = 20;
	private static final String OAUTH_FILE = "OAuth2.json";
	
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
        bind(OAuth2Cred.class).toInstance(configuration.getOauth2());
    }
	
	@Provides
	LobbyDAO provideSpillerDAO () {
        return jdbi.onDemand(LobbyDAO.class);
	}
	
	@Provides @Named("spillerCache")
	Cache<String, Spiller> provideSpillerCache () {
        return CacheBuilder.newBuilder().maximumSize(1000).build();
	}
}
