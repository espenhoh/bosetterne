package com.holtebu.bosetterne.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.dropwizard.Configuration;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skife.jdbi.v2.DBI;

import com.google.common.base.Optional;
import com.google.common.cache.LoadingCache;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.api.SpillerBuilder;
import com.holtebu.bosetterne.service.auth.InjectableOAuthProvider;
import com.holtebu.bosetterne.service.health.TemplateHealthCheck;
import com.holtebu.bosetterne.service.resources.HelloWorldResource;
import com.holtebu.bosetterne.service.resources.BosetterneResource;
import com.holtebu.bosetterne.service.resources.OAuthAccessTokenResource;
import com.holtebu.bosetterne.service.resources.lobby.LobbyResource;
import com.holtebu.bosetterne.service.resources.lobby.LoggInnResource;
import com.holtebu.bosetterne.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.bosetterne.service.resources.lobby.RegistrerResource;
import com.holtebu.bosetterne.service.auth.BosetterneAuthenticator;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;

import static org.mockito.Matchers.any;
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
	private static LobbyDAO daoMock;
	private static BosetterneModule module;
	private static Injector bosetterneInjector;
	
	private InjectableOAuthProvider createdInjectableOAuthProvider;
	private OAuthAccessTokenResource oAuthAccessTokenResource;
	private OAuthAuthorizeResource oAuthAuthorizeResource;
	
	private LobbyResource lobbyResource;
	private HelloWorldResource helloWorldResource;
	private BosetterneResource myResource;
	
	private TemplateHealthCheck templateHealthCheck;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbi = mock(DBI.class); //new DBI("jdbc:mysql://localhost:3306/bosetterne","test","test");
		daoMock = mock(LobbyDAO.class);
		when(dbi.onDemand(any(Class.class))).thenReturn(daoMock);
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
        //Authentication
        InjectableOAuthProvider iop = bosetterneInjector.getInstance(InjectableOAuthProvider.class);
        OAuthAccessTokenResource oaatr = bosetterneInjector.getInstance(OAuthAccessTokenResource.class);
        OAuthAuthorizeResource oaar = bosetterneInjector.getInstance(OAuthAuthorizeResource.class);
        //environment.addProvider(new OAuthProvider<Spiller>(new BosetterneAuthenticator(), "SUPER SECRET STUFF"));        
        
        //Resources
        LobbyResource instance = bosetterneInjector.getInstance(LobbyResource.class);
        RegistrerResource instance2 = bosetterneInjector.getInstance(RegistrerResource.class);
        LoggInnResource instance3 = bosetterneInjector.getInstance(LoggInnResource.class);
        HelloWorldResource instance4 = bosetterneInjector.getInstance(HelloWorldResource.class);
        BosetterneResource instance5 = bosetterneInjector.getInstance(BosetterneResource.class);
        
        //Health checks
        TemplateHealthCheck instance6 = bosetterneInjector.getInstance(TemplateHealthCheck.class);
	}
	
	@Test
	public void testJDBI() throws Exception {
		Environment envMock = mock(Environment.class);
		DBIFactory factory = mock(DBIFactory.class);
		
		DBI realDBI = new DBI("");
		
		when(factory.build(isA(Environment.class), isA(DataSourceFactory.class), isA(String.class))).thenReturn(realDBI);
		
		DBI jdbi = module.getJDBI(envMock, factory);
		
		assertThat("jdbi skal være identiske",jdbi, is(realDBI));
	}
	
	@Test(expected=RuntimeException.class)
	public void testJDBIExit() throws Exception {
		Environment envMock = mock(Environment.class);
		DBIFactory factory = mock(DBIFactory.class);
		
		DBI realDBI = new DBI("");
		
		when(factory.build(isA(Environment.class), isA(DataSourceFactory.class), isA(String.class))).thenThrow(ClassNotFoundException.class);
		
		DBI jdbi = module.getJDBI(envMock, factory);
	}

	/**
	 * Tester at stuben mot database returneres via cache.
	 * @throws Exception
	 */
	@Test
	public void testProvideSpillerCache() throws Exception {
		String bnavn = "brukernavn";
		Spiller expectedSpiller = new SpillerBuilder().withBrukernavn(bnavn).build();
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(expectedSpiller);
		CacheTest cacheTest = bosetterneInjector.getInstance(CacheTest.class);
		
		Spiller actualSpiller = cacheTest.testCache.get(bnavn).get();
				
		assertThat("Cache skal returnere spiller",actualSpiller,is(equalTo(expectedSpiller)));
	}
	
	static final class CacheTest {
		LoadingCache<String, Optional<Spiller>> testCache;
		@Inject
		CacheTest(@Named("spillerCache") LoadingCache<String, Optional<Spiller>> testCache){
			this.testCache = testCache;
		}
	}
}
