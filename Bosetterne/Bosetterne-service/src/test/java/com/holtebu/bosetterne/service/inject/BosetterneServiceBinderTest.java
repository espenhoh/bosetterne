package com.holtebu.bosetterne.service.inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.ConfigurationStub;
import com.holtebu.bosetterne.service.resources.HelloWorldResource;

public class BosetterneServiceBinderTest {
	private BosetterneServiceBinder binder;
	private final String NAME = "BosetterneServiceBinderTest";
	private ServiceLocator locator;
	
	@Mock
	private Environment environmentMock;
	
	@Mock
	private static DBI dbiMock;
	
	@Mock
	private DBIFactory dbiFactoryMock;
	
	private static BosetterneConfiguration config;
	
	@BeforeClass
	public static void setupBeforeClass() throws Exception {
		config = ConfigurationStub.getConf();
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		binder = new BosetterneServiceBinder(dbiFactoryMock);
		binder.setUpEnv(config, environmentMock);
		binder.setUpDao(dbiMock);
		
		locator = ServiceLocatorUtilities.bind(NAME, binder);
	}
	
	@Test
    public void buildsHelloWorldResource() throws Exception {
    	HelloWorldResource resource = locator.getService(HelloWorldResource.class);
    	
    	assertThat("HelloWorldResource skal ha id 1", resource.sayHello(Optional.of("Navn")).getId(), is(1L));
    	assertThat("HelloWorldResource skal ha id 2", resource.sayHello(Optional.of("Navn")).getId(), is(2L));
    	assertThat("HelloWorldResource skal ha Content Hello, Navn!", resource.sayHello(Optional.of("Navn")).getContent(), is("Hello, Navn!"));
    }
	
	@Test
    public void buildsOauthFactory() throws Exception {
		OAuthFactory factory = locator.getService(OAuthFactory.class);
    	
    	assertThat("OauthFactory skal eksistere", factory, is(not(nullValue())));
    }
	
	

	@After
	public void tearDown() throws Exception {
		locator.shutdown();
	}

}
