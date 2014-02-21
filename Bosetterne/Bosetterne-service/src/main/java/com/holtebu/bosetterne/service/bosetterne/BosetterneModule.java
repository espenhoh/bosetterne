package com.holtebu.bosetterne.service.bosetterne;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Names;

public class BosetterneModule extends AbstractModule {
	private final BosetterneConfiguration configuration;
	
	private final Integer INIT_ANTALL_SPILL = 20;
    
	@Inject
	public BosetterneModule(final BosetterneConfiguration configuration) {
		this.configuration = configuration;
	}
	
	@Override
    protected void configure() {
    	bind(BosetterneConfiguration.class).toInstance(configuration);
    	
        bind(String.class).annotatedWith(Names.named("getit")).toInstance("ingenting");
        
        bind(Integer.class).annotatedWith(Names.named("antallSpill")).toInstance(INIT_ANTALL_SPILL);
    }
}
