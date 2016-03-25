package com.holtebu.brettspill.service;

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

import com.holtebu.brettspill.service.core.dao.LobbyDAO;
import com.holtebu.brettspill.service.inject.BosetterneServiceBinder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skife.jdbi.v2.DBI;

public class BosetterneServiceTest {
	@Mock
	private Environment environment;
	@Mock
    private JerseyEnvironment jersey;
	//@Mock
	//private Injector bosetterneInjector;
	@Mock
	private DBI dBIMock;
	@Mock
	private LobbyDAO daoMock;
	
    private BosetterneService application;
    private BosetterneConfiguration config;

    @Before
    public void setup() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	
    	config = ConfigurationStub.getConf();
    	
    	
    	application = new BosetterneService(new BosetterneServiceBinder(new DBIFactory()));
    	
    	
        when(environment.jersey()).thenReturn(jersey);
    }

    @Test
    public void buildsResources() throws Exception {
    	
    }
    
    @Test
    public void testGetName() {
    	assertThat("Skal være Bosetterne - yay!", application.getName(), is(equalTo("Bosetterne - yay!")));
    }

}
