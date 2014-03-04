/**
 * 
 */
package com.holtebu.bosetterne.service.helloworld;

//import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import org.hamcrest.core.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.holtebu.bosetterne.service.bosetterne.BosetterneModule;
import com.holtebu.bosetterne.service.bosetterne.BosetterneConfiguration;
import com.holtebu.bosetterne.service.helloworld.resources.MyResource;

/**
 * @author espen
 * 
 */
public class BosetterneModuleTest {

	@Mock
	private BosetterneConfiguration conf;

	private Injector bosetterneInjector;
	
	private BosetterneModule bosetterneModule;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		bosetterneModule = new BosetterneModule(conf, null);
		bosetterneInjector = Guice.createInjector(bosetterneModule);
	}

	@Test
	public void testConfigure() {
		MyResource myResource = bosetterneInjector.getInstance(MyResource.class);
		assertThat("getit should be \"ingenting\"",myResource.getIt(),is(equalTo("ingenting")));
	}

}
