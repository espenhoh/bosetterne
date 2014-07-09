package com.holtebu.bosetterne.service.resources.lobby;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.util.security.Credential;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.auth.JDBILobbyService;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.resources.lobby.OAuthAuthorizeResource;
import com.holtebu.bosetterne.api.Spiller;

import io.dropwizard.auth.basic.BasicCredentials;

public class OAuthAuthorizeResourceTest {
	
	private static final String CLIENT_ID = "OAuth2TestID";
	
	private static final String CLIENT_SECRET = "OAuth2TestSecret";
	
	private static final String STD_BRUKERNAVN = "brukernavn";
	private static final String STD_PASSORD = "passord";
	private static final String STD_REDIRECT = "redirectUri";
	private static final String STD_CLIENTID = "clientId";
	private static final String STD_STATE = "state";
	private static final String STD_AUTHCODE = UUID.randomUUID().toString();

	private OAuthAuthorizeResource authResource;
	
	private OAuth2Cred auth2Cred;
	
	/*private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;
	
	@Mock
	private LobbyDAO daoMock;*/
	@Mock
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	@Mock
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		auth2Cred = new OAuth2Cred(CLIENT_ID, CLIENT_SECRET);
		
		authResource = new OAuthAuthorizeResource(tokenStore, lobbyService, auth2Cred);
		
		/*CacheLoader<String, Optional<Spiller>> loader = new CacheLoader<String, Optional<Spiller>> () {
			  public Optional<Spiller> load(String key) throws Exception {
				  return Optional.fromNullable(daoMock.finnSpillerVedNavn(key));
			  }
		};

		lobbyService = new JDBILobbyService(
			CacheBuilder.newBuilder()
				.maximumSize(1000)
				.expireAfterWrite(10, TimeUnit.MINUTES)
				.build(loader)
			);
		tokenStore = new PolettlagerIMinne(new HashMap<String, Spiller>(),new HashMap<String, Legitimasjon>(),new OAuth2Cred("id","secret"));*/
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void spillerAutorisert() throws Exception {
		Response respons = authResource.login(STD_BRUKERNAVN, STD_PASSORD, STD_REDIRECT, STD_CLIENTID, STD_STATE);
		
		assertThat("Spiller autentisert.",respons.getStatus(), is(equalTo(Response.Status.ACCEPTED.getStatusCode())));
	}
	
	/** Given/When/Then syntax*/
	@Test
	public void narSpilletIkkeKjennerBrukernavnSkalResponsenHaStatusUNAUTHORIZED(){
		String brukernavn = "Ikke kjent brukernavn";
		String passord = "galtPassord";
		
		Optional<Spiller> ukjentSpiller = Optional.absent();
		when(lobbyService.getSpiller(isA(BasicCredentials.class))).thenReturn(ukjentSpiller);
		
		Response respons = authResource.login(brukernavn, passord, STD_REDIRECT, STD_CLIENTID, STD_STATE);
		
		assertThat("Responsen skal ha status UNAUTHORIZED", respons.getStatus(), is(equalTo(Response.Status.UNAUTHORIZED.getStatusCode())));
	}
	
	/** Given/When/Then syntax*/
	@Test
	public void narSpilletIkkeKjennerBrukernavnSkalResponsenHaSgtatusUNAUTHORIZED(){
		String brukernavn = "Ikke kjent brukernavn";
		String passord = "galtPassord";
		
		Optional<Spiller> ukjentSpiller = Optional.absent();
		when(lobbyService.getSpiller(isA(BasicCredentials.class))).thenReturn(ukjentSpiller);
		
		Response respons = authResource.login(brukernavn, passord, STD_REDIRECT, STD_CLIENTID, STD_STATE);
		
		assertThat("Responsen skal ha status UNAUTHORIZED", respons.getStatus(), is(equalTo(Response.Status.UNAUTHORIZED.getStatusCode())));
	}


}
