package com.holtebu.bosetterne.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skife.jdbi.v2.DBI;

import com.google.inject.Injector;
import com.holtebu.bosetterne.service.auth.InjectableOAuthProvider;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.resources.BosetterneResource;
import com.holtebu.bosetterne.service.resources.HelloWorldResource;
import com.holtebu.bosetterne.service.resources.OAuthAccessTokenResource;
import com.holtebu.bosetterne.service.resources.lobby.LobbyResource;
import com.holtebu.bosetterne.service.resources.lobby.LoggInnResource;
import com.holtebu.bosetterne.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.bosetterne.service.resources.lobby.RegistrerResource;

public class BosetterneServiceTest {
	@Mock
	private Environment environment;
	@Mock
    private JerseyEnvironment jersey;
	@Mock
	private Injector bosetterneInjector;
	
    private BosetterneModule bosetterneModule;
    private BosetterneService application;
    private BosetterneConfiguration config;

    @Before
    public void setup() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	
    	bosetterneModule = spy(new BosetterneModule()); 
    	application = new BosetterneService(bosetterneModule);
    	
    	config = ConfigurationStub.getConf();
        when(environment.jersey()).thenReturn(jersey);
    }

    @Test
    public void buildsResources() throws Exception {
    	doReturn(mock(DBI.class)).when(bosetterneModule).getJDBI(eq(environment), isA(DBIFactory.class));

    	when(bosetterneInjector.getInstance(eq(InjectableOAuthProvider.class))).thenReturn(new InjectableOAuthProvider<>(null, ""));
    	when(bosetterneInjector.getInstance(eq(OAuthAuthorizeResource.class))).thenReturn(new OAuthAuthorizeResource(null, null, null));
    	when(bosetterneInjector.getInstance(eq(OAuthAccessTokenResource.class))).thenReturn(new OAuthAccessTokenResource(null, null, null));
    	when(bosetterneInjector.getInstance(eq(LobbyResource.class))).thenReturn(new LobbyResource(null));
    	when(bosetterneInjector.getInstance(eq(RegistrerResource.class))).thenReturn(new RegistrerResource(null));
    	when(bosetterneInjector.getInstance(eq(LoggInnResource.class))).thenReturn(new LoggInnResource());
    	when(bosetterneInjector.getInstance(eq(HelloWorldResource.class))).thenReturn(new HelloWorldResource(config, null));
    	when(bosetterneInjector.getInstance(eq(BosetterneResource.class))).thenReturn(new BosetterneResource(null,null));

    	
        application.run(config, environment);

        verify(jersey).register(isA(InjectableOAuthProvider.class));
        verify(jersey).register(isA(OAuthAccessTokenResource.class));
        verify(jersey).register(isA(OAuthAuthorizeResource.class));
        verify(jersey).register(isA(LobbyResource.class));
        verify(jersey).register(isA(RegistrerResource.class));
        verify(jersey).register(isA(LoggInnResource.class));
        verify(jersey).register(isA(HelloWorldResource.class));
        verify(jersey).register(isA(BosetterneResource.class));
    }
    
    @Test
    public void testGetName() {
    	assertThat("Skal være Bosetterne - yay!", application.getName(), is(equalTo("Bosetterne - yay!")));
    }

}