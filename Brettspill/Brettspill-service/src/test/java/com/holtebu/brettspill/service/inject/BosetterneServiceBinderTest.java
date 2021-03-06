package com.holtebu.brettspill.service.inject;

import static com.holtebu.brettspill.api.lobby.SpillerBuilder.lagTestspiller;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.holtebu.brettspill.service.OAuth2Cred;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.core.AccessToken;
import com.holtebu.brettspill.service.core.Legitimasjon;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

import java.io.InputStream;
import java.net.URI;
import java.util.*;

import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Optional;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.BosetterneConfiguration;
import com.holtebu.brettspill.service.ConfigurationStub;
import com.holtebu.brettspill.service.core.dao.LobbyDAO;
import com.holtebu.brettspill.service.resources.HelloWorldResource;
import com.holtebu.brettspill.service.resources.OAuthAccessTokenResource;
import com.holtebu.brettspill.service.resources.lobby.LobbyResource;
import com.holtebu.brettspill.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.brettspill.service.resources.lobby.RegistrerResource;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.*;

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
		when(dbiFactoryMock.build(environmentMock, config.getDataSourceFactory(), "mySQL")).thenReturn(dbiMock);
		when(dbiMock.onDemand(LobbyDAO.class)).thenReturn(lobbyDAOMock);

		binder = new BosetterneServiceBinder(dbiFactoryMock, config, environmentMock);
		binder.setUpEnv(config, environmentMock);
		

		
		locator = ServiceLocatorUtilities.bind(NAME, binder);
	}
	
	@Test
    public void buildsHelloWorldResource() throws Exception {
    	HelloWorldResource resource = locator.getService(HelloWorldResource.class);
    	
//    	assertThat("HelloWorldResource skal ha id 1", resource.sayHello("Navn").getId(), is(1L));
//    	assertThat("HelloWorldResource skal ha id 2", resource.sayHello("Navn").getId(), is(2L));
//    	assertThat("HelloWorldResource skal ha Content Hello, Navn!", resource.sayHello("Navn").getContent(), is("Hello, Navn!"));
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
	

	@After
	public void tearDown() throws Exception {
		locator.shutdown();
	}

	private ContainerRequestContext requestContext(final MultivaluedMap<String, String> headerz) {
		return new ContainerRequestContext() {

			SecurityContext context;
			@Override
			public Object getProperty(String name) {
				return null;
			}

			@Override
			public Collection<String> getPropertyNames() {
				return null;
			}

			@Override
			public void setProperty(String name, Object object) {

			}

			@Override
			public void removeProperty(String name) {

			}

			@Override
			public UriInfo getUriInfo() {
				return null;
			}

			@Override
			public void setRequestUri(URI requestUri) {

			}

			@Override
			public void setRequestUri(URI baseUri, URI requestUri) {

			}

			@Override
			public Request getRequest() {
				return null;
			}

			@Override
			public String getMethod() {
				return null;
			}

			@Override
			public void setMethod(String method) {

			}

			@Override
			public MultivaluedMap<String, String> getHeaders() {
				return headerz;
			}

			@Override
			public String getHeaderString(String name) {
				return null;
			}

			@Override
			public Date getDate() {
				return null;
			}

			@Override
			public Locale getLanguage() {
				return null;
			}

			@Override
			public int getLength() {
				return 0;
			}

			@Override
			public MediaType getMediaType() {
				return null;
			}

			@Override
			public List<MediaType> getAcceptableMediaTypes() {
				return null;
			}

			@Override
			public List<Locale> getAcceptableLanguages() {
				return null;
			}

			@Override
			public Map<String, Cookie> getCookies() {
				return null;
			}

			@Override
			public boolean hasEntity() {
				return false;
			}

			@Override
			public InputStream getEntityStream() {
				return null;
			}

			@Override
			public void setEntityStream(InputStream input) {

			}

			@Override
			public SecurityContext getSecurityContext() {
				return context;
			}

			@Override
			public void setSecurityContext(SecurityContext context) {
				this.context = context;
			}

			@Override
			public void abortWith(Response response) {

			}
		};
	}
}
