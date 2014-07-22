package com.holtebu.bosetterne.service.resources;

import io.dropwizard.auth.basic.BasicCredentials;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.BosetterneModule;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.auth.JDBILobbyService;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.resources.lobby.OAuthAuthorizeResource;
import com.sun.jersey.test.framework.JerseyTest;

public class OAuthAccessTokenResourceTest{
	
	
	
	private static final String STD_BRUKERNAVN = "brukernavn";
	private static final String STD_PASSORD = "passord";
	private static final String STD_GRANT_TYPE = "code";
	private static final String STD_REDIRECT = "redirectUri";
	private static final String STD_CLIENTID = "clientId";
	private static final String STD_CLIENT_SECRET = "OAuth2TestSecret";
	private static final String STD_CODE = "code";
	private static final String STD_SCOPE = "bosetterne";
	private static final String STD_AUTHCODE = UUID.randomUUID().toString();

	
	@Mock
	private LobbyDAO daoMock;
	
	private OAuth2Cred auth2Cred;
	
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;
	
	private Map<String, Spiller> accessTokens;
	private Map<String, Legitimasjon> codes;
	
	private OAuthAccessTokenResource authResource;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		lobbyService = new JDBILobbyService(new BosetterneModule().provideSpillerCache(daoMock));
		auth2Cred = new OAuth2Cred(STD_CLIENTID, STD_CLIENT_SECRET);
		accessTokens = new HashMap<>();
		codes = new HashMap<>();
		tokenStore = new PolettlagerIMinne(accessTokens, codes, auth2Cred);
		
		authResource = new OAuthAccessTokenResource(tokenStore, lobbyService, auth2Cred);
	}
	
	@Test(expected=WebApplicationException.class)
	public void hvisAuthorizationHeaderErNullSkalExceptionKastes() throws Exception {
		authResource.accessToken(null, STD_CLIENTID, STD_CLIENT_SECRET, STD_GRANT_TYPE, STD_CODE, STD_REDIRECT);
	}
	
	

}
