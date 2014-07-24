package com.holtebu.bosetterne.service.resources;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
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
import com.holtebu.bosetterne.api.SpillerBuilder;
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
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.test.framework.JerseyTest;

public class OAuthAccessTokenResourceTest{
	
	
	private static final String STD_BRUKERNAVN = "brukernavn";
	private static final String STD_PASSORD = "passord";
	private static final String STD_AUTHENTICATION = "Basic " + new String(Base64.encode(STD_BRUKERNAVN + ":" + STD_PASSORD));
	private static final String STD_GRANT_TYPE = "code";
	private static final String STD_REDIRECT = "redirectUri";
	private static final String STD_CLIENTID = "clientId";
	private static final String STD_CLIENT_SECRET = "OAuth2TestSecret";
	private static final String STD_CODE = "code";
	private static final String STD_SCOPE = "bosetterne";
	private static final String STD_AUTHCODE = UUID.randomUUID().toString();
	private static final String STD_ACCESSTOKEN = UUID.randomUUID().toString();


	
	private OAuth2Cred auth2Cred;
	
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;
	
	private Map<String, Spiller> accessTokens;
	private Map<String, Legitimasjon> codes;
	
	private OAuthAccessTokenResource authResource;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		//lobbyService = new JDBILobbyService(new BosetterneModule().provideSpillerCache(daoMock));
		auth2Cred = new OAuth2Cred(STD_CLIENTID, STD_CLIENT_SECRET);
		accessTokens = new HashMap<>();
		codes = new HashMap<>();
		tokenStore = new PolettlagerIMinne(accessTokens, codes, auth2Cred);
		
		authResource = new OAuthAccessTokenResource(tokenStore, null, auth2Cred);
	}
	
	@Test(expected=WebApplicationException.class)
	public void hvisAuthorizationHeaderErNullSkalExceptionKastes() throws Exception {
		authResource.accessToken(null, STD_CLIENTID, STD_CLIENT_SECRET, STD_GRANT_TYPE, STD_CODE, STD_REDIRECT);
	}
	
	@Test(expected=WebApplicationException.class)
	public void narSpillerIkkeBlirFunnetSkalExceptionKastes() throws Exception {
		authResource.accessToken(STD_AUTHENTICATION, STD_CLIENTID, STD_CLIENT_SECRET, STD_GRANT_TYPE, STD_CODE, STD_REDIRECT);
	}

	@Test(expected=WebApplicationException.class)
	public void narAltErNullSkalExceptionKastes() throws Exception {
		authResource.accessToken(null, null, null, null, null, null);
	}
	
	
	@Test
	public void medKorrektAutorisasjonSkalAccessTokenReturneres() throws Exception {
		Spiller spiller = returnerStdSpiller();
		
		Legitimasjon leg = new Legitimasjon().setClientId(STD_CLIENTID).setSecret(STD_CLIENT_SECRET).setCode(STD_CODE).setRedirectUri(STD_REDIRECT).setResponseType("token").setSpiller(spiller);
		
		AccessToken stdToken = authResource.accessToken(STD_AUTHENTICATION, STD_CLIENTID, STD_CLIENT_SECRET, STD_GRANT_TYPE, STD_CODE, STD_REDIRECT);
		
		assertThat("Token skal ikke være null", stdToken, not(nullValue()));
		assertThat("Scope skal være bosetterne", stdToken.getScope(), is(equalTo("bosetterne")));
	}
	
	private Spiller returnerStdSpiller() {
		Spiller spiller = new SpillerBuilder().withBrukernavn(STD_BRUKERNAVN).withPassord(STD_PASSORD).build();
		Legitimasjon leg = new Legitimasjon().setClientId(STD_CLIENTID).setSecret(STD_CLIENT_SECRET).setCode(STD_CODE).setRedirectUri(STD_REDIRECT).setResponseType("token").setSpiller(spiller);
		codes.put(STD_CODE, leg);
		return spiller;
	}
}