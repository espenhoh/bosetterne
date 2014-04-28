package com.holtebu.bosetterne.service.auth.sesjon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

import org.hamcrest.core.IsNull;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.internal.runners.statements.Fail;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.auth.JDBILobbyService;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class PolettlagerIMinneTest {
	
	private static Legitimasjon oAuth2Verdier;
	
	private PolettlagerIMinne polettLager;
	
	private Map<String, Spiller> accessTokens;
	private Map<String, Spiller> codes;
	
	@BeforeClass
	public static void setUpBeforeClass() {
        oAuth2Verdier = new Legitimasjon().setClientId("DummyClientID").setSecret("DummySecret");
	}

	@Before
	public void setUp() {
		accessTokens = new HashMap<String, Spiller>();
		codes = new HashMap<String, Spiller>();
		polettLager = new PolettlagerIMinne(accessTokens, codes, oAuth2Verdier);
	}


	@Test
	public void storeAccessTokenFeilerIkke() {
		String username = "test";
		String password = "testPassord";
		Spiller spiller = new Spiller(username, password, "", true, null, null);
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret(oAuth2Verdier.getSecret()).setSpiller(spiller);
		
		AccessToken token = polettLager.storeAccessToken(leg);
		
		assertThat("Spiller skal være lagt i map", accessTokens.get(token.getAccess_token()),is(spiller));
	}
	
	@Test
	public void storeAccessTokenClientIdFeiler() {
		String username = "test";
		String password = "testPassord";
		Spiller spiller = new Spiller(username, password, "", true, null, null);
		Legitimasjon leg = new Legitimasjon().setClientId("feiler").setSecret(oAuth2Verdier.getSecret()).setSpiller(spiller);
		
		AccessToken token = polettLager.storeAccessToken(leg);
		
		assertThat("accessToken skal være null", token, is(nullValue()));
		assertThat("Map skal være tomt", accessTokens.isEmpty(),is(equalTo(true)));
	}
	
	@Test
	public void storeAccessTokenSecretFeiler() {
		String username = "test";
		String password = "testPassord";
		Spiller spiller = new Spiller(username, password, "", true, null, null);
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret("feiler").setSpiller(spiller);
		
		AccessToken token = polettLager.storeAccessToken(leg);
		
		assertThat("accessToken skal være null", token, is(nullValue()));
		assertThat("Map skal være tomt", accessTokens.isEmpty(),is(equalTo(true)));
	}

	@Test
	public void testGetSpillerByAuthorizationCode() throws Exception {
		org.junit.Assert.fail("not yet implemented");
	}

	@Test
	public void verifyClientSecretOK() throws Exception {
		Legitimasjon leg = new Legitimasjon().setSecret(oAuth2Verdier.getSecret());
		
		boolean clientSecretOK = polettLager.verifyClientSecret(leg);
		
		assertThat("Client secret skal ha samme verdi som fra json", clientSecretOK, is(true));
	}
	
	@Test
	public void verifyClientSecretFeil() throws Exception {
		Legitimasjon leg = new Legitimasjon().setSecret("Noe annet");
		
		boolean clientSecretOK = polettLager.verifyClientSecret(leg);
		
		assertThat("Client secret skal ha forskjellig verdi som json og dermed være false", clientSecretOK, is(false));
	}
	
	@Test
	public void verifyClientSecretNull() throws Exception {
		Legitimasjon leg = new Legitimasjon();
		
		boolean clientSecretOK = polettLager.verifyClientSecret(leg);
		
		assertThat("Client secret skal være null og dermed verifisert false", clientSecretOK, is(false));
	}
	
	@Test
	public void verifyClientIdOK() throws Exception {
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId());
		
		boolean clientIdOK = polettLager.verifyClientId(leg);
		
		assertThat("ClientId skal ha samme verdi som fra json", clientIdOK, is(true));
	}
	
	@Test
	public void verifyClientIdFeil() throws Exception {
		Legitimasjon leg = new Legitimasjon().setClientId("Noe annet");
		
		boolean clientIdOK = polettLager.verifyClientId(leg);
		
		assertThat("ClientId skal ha forskjellig verdi som json og dermed være false", clientIdOK, is(false));
	}
	
	@Test
	public void verifyClientIdNull() throws Exception {
		Legitimasjon leg = new Legitimasjon();
		
		boolean clientIdOK = polettLager.verifyClientId(leg);
		
		assertThat("Client secret skal være null og dermed verifisert false", clientIdOK, is(false));
	}

}
