package com.holtebu.bosetterne.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.dropwizard.auth.Authenticator;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skife.jdbi.v2.DBI;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.auth.InjectableOAuthProvider;
import com.holtebu.bosetterne.service.health.TemplateHealthCheck;
import com.holtebu.bosetterne.service.resources.HelloWorldResource;
import com.holtebu.bosetterne.service.resources.BosetterneResource;
import com.holtebu.bosetterne.service.resources.OAuthAccessTokenResource;
import com.holtebu.bosetterne.service.resources.lobby.LobbyResource;
import com.holtebu.bosetterne.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.bosetterne.service.auth.BosetterneAuthenticator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class BosetterneModuleTest {
	
	private static DBI dbi;
	private static BosetterneModule module;
	private  static Injector bosetterneInjector;
	
	private InjectableOAuthProvider createdInjectableOAuthProvider;
	private OAuthAccessTokenResource oAuthAccessTokenResource;
	private OAuthAuthorizeResource oAuthAuthorizeResource;
	
	private LobbyResource lobbyResource;
	private HelloWorldResource helloWorldResource;
	private BosetterneResource myResource;
	
	private TemplateHealthCheck templateHealthCheck;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbi = new DBI("jdbc:mysql://localhost:3306/bosetterne","test","test");
		module = new BosetterneModule(ConfigurationStub.getConf(), dbi);
		bosetterneInjector = Guice.createInjector(module);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	

	@Before
	public void setUp() throws Exception {

		//Authentication
		
		oAuthAccessTokenResource = bosetterneInjector.getInstance(OAuthAccessTokenResource.class);
		oAuthAuthorizeResource = bosetterneInjector.getInstance(OAuthAuthorizeResource.class);
        //environment.addProvider(new OAuthProvider<Spiller>(new BosetterneAuthenticator(), "SUPER SECRET STUFF"));        
        
        //Resources
		lobbyResource = bosetterneInjector.getInstance(LobbyResource.class);
		helloWorldResource = bosetterneInjector.getInstance(HelloWorldResource.class);
        myResource = bosetterneInjector.getInstance(BosetterneResource.class);
        
        //Health checks
        templateHealthCheck = bosetterneInjector.getInstance(TemplateHealthCheck.class);
        
        //Filter
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void createAuthenticator() {
		Authenticator<String, Spiller> createdAuthenticator1 = bosetterneInjector.getInstance(BosetterneAuthenticator.class);
		Authenticator<String, Spiller> createdAuthenticator2 = bosetterneInjector.getInstance(BosetterneAuthenticator.class);
		
		assertThat("BosetterneAuthenticator skal være singleton", createdAuthenticator1 == createdAuthenticator2);
		//InjectableOAuthProvider injectableOAuthProvider = new InjectableOAuthProvider<Spiller>(authenticator, realm);
		
		//createdInjectableOAuthProvider = bosetterneInjector.getInstance(InjectableOAuthProvider.class);
		
	}
	
	@Test
	public void createInjectableOAuthProvider() {
		String realm = module.REALM;
		Authenticator<String, Spiller> authenticator;
		//InjectableOAuthProvider injectableOAuthProvider = new InjectableOAuthProvider<Spiller>(authenticator, realm);
		
		createdInjectableOAuthProvider = bosetterneInjector.getInstance(InjectableOAuthProvider.class);
		
	}
	
	

	@Test
	public void testConfigure() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testProvideSpillerDAO() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testProvideSpillerCache() throws Exception {
		
		
		
		throw new RuntimeException("not yet implemented");
	}

}
