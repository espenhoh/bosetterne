package com.holtebu.bosetterne.service.inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
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
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.resources.BosetterneResource;
import com.holtebu.bosetterne.service.resources.HelloWorldResource;
import com.holtebu.bosetterne.service.resources.OAuthAccessTokenResource;
import com.holtebu.bosetterne.service.resources.lobby.LobbyResource;
import com.holtebu.bosetterne.service.resources.lobby.LoggInnResource;
import com.holtebu.bosetterne.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.bosetterne.service.resources.lobby.RegistrerResource;

public class BosetterneServiceBinderTest {
	private BosetterneServiceBinder binder;
	private final String NAME = "BosetterneServiceBinderTest";
	private ServiceLocator locator;
	
	@Mock
	private Environment environmentMock;
	
	@Mock
	private DBI dbiMock;
	
	@Mock
	private LobbyDAO lobbyDAOMock;
	
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
		
		when(dbiMock.onDemand(LobbyDAO.class)).thenReturn(lobbyDAOMock);
		
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
    public void buildsOAuthAccessTokenResource() throws Exception {
		OAuthAccessTokenResource oaatr = locator.getService(OAuthAccessTokenResource.class);
		OAuthAccessTokenResource oaatr2 = locator.getService(OAuthAccessTokenResource.class);
		assertThat("OAuthAccessTokenResource skal eksistere", oaatr, is(not(nullValue())));
    	assertThat("OAuthAccessTokenResource skal være like", oaatr, is(oaatr2));
    }
	
	@Test
    public void buildsOAuthAuthorizeResource() throws Exception {
		OAuthAuthorizeResource oaar = locator.getService(OAuthAuthorizeResource.class);
		OAuthAuthorizeResource oaar2 = locator.getService(OAuthAuthorizeResource.class);
		assertThat("OAuthAuthorizeResource skal eksistere", oaar, is(not(nullValue())));
    	assertThat("OAuthAuthorizeResource skal være like", oaar, is(oaar2));
    }
	
	@Test
    public void buildsLobbyResource() throws Exception {
		LobbyResource lr = locator.getService(LobbyResource.class);
		LobbyResource lr2 = locator.getService(LobbyResource.class);
		assertThat("OAuthAuthorizeResource skal eksistere", lr, is(not(nullValue())));
    	assertThat("OAuthAuthorizeResource skal være like", lr, is(lr2));
    }
	
	@Test
    public void buildsRegistrerResource() throws Exception {
		RegistrerResource lr = locator.getService(RegistrerResource.class);
		RegistrerResource lr2 = locator.getService(RegistrerResource.class);
		assertThat("RegistrerResource skal eksistere", lr, is(not(nullValue())));
    	assertThat("RegistrerResource skal være like", lr, is(lr2));
    }
	
	@Test
    public void buildsLoggInnResource() throws Exception {
		LoggInnResource lr = locator.getService(LoggInnResource.class);
		LoggInnResource lr2 = locator.getService(LoggInnResource.class);
		assertThat("LoggInnResource skal eksistere", lr, is(not(nullValue())));
    	assertThat("LoggInnResource skal være like", lr, is(lr2));
    }
	
	@Test
    public void buildsBosetterneResource() throws Exception {
		BosetterneResource lr = locator.getService(BosetterneResource.class);
		BosetterneResource lr2 = locator.getService(BosetterneResource.class);
		assertThat("BosetterneResource skal eksistere", lr, is(not(nullValue())));
    	assertThat("BosetterneResource skal være like", lr, is(lr2));
    }
	
	

	@After
	public void tearDown() throws Exception {
		locator.shutdown();
	}

}
