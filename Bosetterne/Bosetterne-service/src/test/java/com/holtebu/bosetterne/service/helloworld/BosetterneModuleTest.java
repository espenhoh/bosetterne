/**
 * 
 */
package com.holtebu.bosetterne.service.helloworld;

//import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;

import org.hamcrest.core.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skife.jdbi.v2.DBI;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.BosetterneModule;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.resources.BosetterneResource;

import io.dropwizard.setup.Environment;

/**
 * @author espen
 * 
 */
public class BosetterneModuleTest {

	private BosetterneConfiguration conf;
	
	@Mock
	private Environment env;
	
	private static DBI dbi;

	private Injector bosetterneInjector;
	
	private BosetterneModule bosetterneModule;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		dbi = new DBI("jdbc:mysql://localhost:3306/bosetterne",
//	            "hibernate",
//	            "G6$4¤fgH5ZX");
	}
	


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		conf = com.holtebu.bosetterne.service.ConfigurationStub.getConf();
		bosetterneModule = new BosetterneModule(conf, env);
		bosetterneInjector = Guice.createInjector(bosetterneModule);
	}

	@Test
	public void testConfigure() {
		BosetterneResource myResource = bosetterneInjector.getInstance(BosetterneResource.class);
		assertThat("getit should be \"ingenting\"",myResource.getIt(),is(equalTo("ingenting")));
	}

}
