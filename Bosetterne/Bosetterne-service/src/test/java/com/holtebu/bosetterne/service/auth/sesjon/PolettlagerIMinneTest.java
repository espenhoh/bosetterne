package com.holtebu.bosetterne.service.auth.sesjon;

import java.io.File;
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
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.ConfigurationStub;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import io.dropwizard.auth.basic.BasicCredentials;

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

import com.google.common.base.Optional;

public class PolettlagerIMinneTest {
	
	private static OAuth2Cred oAuth2Verdier;
	
	private PolettlagerIMinne polettLager;
	
	private Map<String, Spiller> accessTokens;
	private Map<String, Legitimasjon> codes;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
        oAuth2Verdier = ConfigurationStub.getConf().getOauth2();
	}

	@Before
	public void setUp() {
		accessTokens = new HashMap<String, Spiller>();
		codes = new HashMap<String, Legitimasjon>();
		polettLager = new PolettlagerIMinne(accessTokens, codes, oAuth2Verdier);
	}


	@Test
	public void storeAccessTokenFeilerIkke() {
		Spiller spiller = lagTestspiller();
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		
		AccessToken token = polettLager.storeAccessToken(leg);
		
		assertThat("Spiller skal være lagt i map", accessTokens.get(token.getAccess_token()),is(spiller));
	}
	
	@Test
	public void storeAccessTokenClientIdFeiler() {
		Spiller spiller = lagTestspiller();
		Legitimasjon leg = new Legitimasjon().setClientId("feiler").setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		
		AccessToken token = polettLager.storeAccessToken(leg);
		
		assertThat("accessToken skal være null", token, is(nullValue()));
		assertThat("Map skal være tomt", accessTokens.isEmpty(),is(equalTo(true)));
	}
	
	@Test
	public void storeAccessTokenSecretFeiler() {
		Spiller spiller = lagTestspiller();
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret("feiler").setSpiller(spiller);
		
		AccessToken token = polettLager.storeAccessToken(leg);
		
		assertThat("accessToken skal være null", token, is(nullValue()));
		assertThat("Map skal være tomt", accessTokens.isEmpty(),is(equalTo(true)));
	}


	@Test
	public void storeAccessTokenLegNull() {
		AccessToken token = polettLager.storeAccessToken(null);
		
		assertThat("accessToken skal være null", token, is(nullValue()));
		assertThat("Map skal være tomt", accessTokens.isEmpty(),is(equalTo(true)));
	}

	@Test
	public void testGetSpillerByAuthorizationCode() throws Exception {
		Spiller spiller = lagTestspiller();
		Legitimasjon leg = new Legitimasjon().setClientId("feiler").setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		String authCode = UUID.randomUUID().toString();
		codes.put(authCode, leg);
		
		Optional<Spiller> hentetSpiller = polettLager.getSpillerByAuthorizationCode(authCode);
		
		assertThat("Hentet spiller skal eksistere", hentetSpiller.isPresent(), is(equalTo(true)));
		assertThat("Spillere skal være identiske", hentetSpiller.get(), is(spiller));
	}
	
	@Test
	public void testGetSpillerByAuthorizationCodeIkkeEksisterer() throws Exception {
		Optional<Spiller> hentetSpiller = polettLager.getSpillerByAuthorizationCode("");
		
		assertThat("Hentet spiller skal ikke eksistere", hentetSpiller.isPresent(), is(equalTo(false)));
	}

	@Test
	public void verifyClientSecretOK() throws Exception {
		Legitimasjon leg = new Legitimasjon().setSecret(oAuth2Verdier.getClientSecret());
		
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
	
	@Test
	public void verifyClientSecretLegNull() throws Exception {
		boolean clientSecretOK = polettLager.verifyClientSecret(null);
		
		assertThat("Legitimasjon skal være null og dermed verifisert false", clientSecretOK, is(false));
	}
	
	@Test
	public void verifyClientIdLegNull() throws Exception {
		boolean clientIdOK = polettLager.verifyClientId(null);
		
		assertThat("Legitimasjon skal være null og dermed verifisert false", clientIdOK, is(false));
	}

	@Test
	public void testGetSpillerByAccessToken() throws Exception {
		Spiller spiller = lagTestspiller();
		String accessToken = UUID.randomUUID().toString();
		accessTokens.put(accessToken, spiller);
		
		Optional<Spiller> hentetSpiller = polettLager.getSpillerByAccessToken(accessToken);
		
		assertThat("Hentet spiller skal eksistere", hentetSpiller.isPresent(), is(equalTo(true)));
		assertThat("Spillere skal være identiske", hentetSpiller.get(), is(spiller));
	}
	
	@Test
	public void testGetSpillerByAccessTokenIkkeEksisterer() throws Exception {
		Optional<Spiller> hentetSpiller = polettLager.getSpillerByAccessToken("");

		assertThat("Hentet spiller skal ikke eksistere", hentetSpiller.isPresent(), is(equalTo(false)));
	}
	
	@Test
	public void testStoreAuthorizationCode() throws Exception {
		Spiller spiller = lagTestspiller();
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		
		String code = polettLager.storeAuthorizationCode(leg);
		
		assertThat("Legitimasjon skal være lagt i map", codes.get(code),is(leg));
	}
	
	@Test
	public void testStoreAuthorizationCodeClientIdFeiler() {
		Spiller spiller = lagTestspiller();
		Legitimasjon leg = new Legitimasjon().setClientId("feiler").setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		
		String code = polettLager.storeAuthorizationCode(leg);
		
		assertThat("AuthorizationCode skal være null", code, is(nullValue()));
		assertThat("Map skal være tomt", codes.isEmpty(),is(equalTo(true)));
	}

	@Test
	public void testStoreAuthorizationCodeLegNull() {
		String code = polettLager.storeAuthorizationCode(null);
		
		assertThat("AuthorizationCode skal være null", code, is(nullValue()));
		assertThat("Map skal være tomt", codes.isEmpty(),is(equalTo(true)));
	}
	
	private Spiller lagTestspiller() {
		String username = "test";
		String password = "testPassord";
		Spiller spiller = new Spiller(username, password, "", true, null, null);
		return spiller;
	}

}