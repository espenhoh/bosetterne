package com.holtebu.brettspill.service.auth.sesjon;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.holtebu.brettspill.service.OAuth2Cred;
import com.holtebu.brettspill.service.core.AccessToken;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.ConfigurationStub;
import com.holtebu.brettspill.service.core.Legitimasjon;

import io.dropwizard.auth.basic.BasicCredentials;

import org.hamcrest.core.IsNull;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.internal.runners.statements.Fail;
import org.junit.rules.ExpectedException;
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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static com.holtebu.brettspill.api.lobby.SpillerBuilder.lagTestspiller;

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
	public void storeAccessTokenFeilerIkke() throws Exception{
		Spiller spiller = lagTestspiller();
		spiller.setInnlogget(false);
		
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		
		AccessToken token = polettLager.storeAccessToken(leg);
		
		assertThat("Spiller skal være lagt i map", accessTokens.get(token.getAccessToken()),is(spiller));
		assertThat("Spilleren skal være logget inn", spiller.isInnlogget(), is(true));
	}
	
	
	@Test
	public void storeAccessTokenClientIdFeiler(){
		Spiller spiller = lagTestspiller();
		Legitimasjon leg = new Legitimasjon().setClientId("feiler").setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		
		AccessToken token = null;
		try {
			token = polettLager.storeAccessToken(leg);
		} catch (AutorisasjonsException e) {
			assertThat("accessToken skal være null", token, is(nullValue()));
			assertThat("Map skal være tomt", accessTokens.isEmpty(),is(equalTo(true)));
			return;
		}
		fail("Skal kaste AutorisajonsException");
	}
	
	@Test
	public void storeAccessTokenSecretFeiler() throws Exception {
		Spiller spiller = lagTestspiller();
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret("feiler").setSpiller(spiller);
		
		AccessToken token = polettLager.storeAccessToken(leg);
		
		assertThat("accessToken skal være null", token, is(nullValue()));
		assertThat("Map skal være tomt", accessTokens.isEmpty(),is(equalTo(true)));
	}


	@Test(expected=AutorisasjonsException.class)
	public void storeAccessTokenLegNull() throws Exception {
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
	public void verifyClientSecretLegNull() throws Exception {
		boolean clientSecretOK = polettLager.verifyClientSecret(null);
		
		assertThat("Legitimasjon skal være null og dermed verifisert false", clientSecretOK, is(false));
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
	
	@Test(expected=AutorisasjonsException.class)
	public void testStoreAuthorizationCodeClientIdFeiler() throws Exception {
		Spiller spiller = lagTestspiller();
		Legitimasjon leg = new Legitimasjon().setClientId("feiler").setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		
		String code = polettLager.storeAuthorizationCode(leg);
		
		//assertThat("AuthorizationCode skal være null", code, is(nullValue()));
		//assertThat("Map skal være tomt", codes.isEmpty(),is(equalTo(true)));
	}

	@Test(expected=AutorisasjonsException.class)
	public void testStoreAuthorizationCodeLegNull() throws Exception {
		String code = polettLager.storeAuthorizationCode(null);
		
		assertThat("AuthorizationCode skal være null", code, is(nullValue()));
		assertThat("Map skal være tomt", codes.isEmpty(),is(equalTo(true)));
	}
	
	
	@Test
	public void whenAskedForAccessTokenRemoveAuthCode() throws AutorisasjonsException{
		Spiller spiller = lagTestspiller();
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		
		String code = polettLager.storeAuthorizationCode(leg);
		polettLager.getSpillerByAuthorizationCode(code);
		
		assertThat("Spilleren skal ikke kunne hentes med autoriseringskode 2 ganger", polettLager.getSpillerByAuthorizationCode(code).isPresent(), is(false));
	}
	
	@Test
	public void innloggedeSpillereSkalVæreIdentiskMedAccessTokenMap() {
		
		Map<String, Spiller> innloggedeSpillere = polettLager.getInnloggedeSpillere();
		
		assertSame(accessTokens, innloggedeSpillere);
	}
	
	@Test
	public void ifPlayerLoggedOutAccessTokenShouldNotWork() throws Exception {
		Spiller spiller = lagTestspiller();
		spiller.setInnlogget(true);
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		Spiller spiller2 = lagTestspiller();
		spiller2.setBrukernavn("Brukernavn2");
		Legitimasjon leg2 = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller2);

		
		String code = polettLager.storeAuthorizationCode(leg);
		String code2 = polettLager.storeAuthorizationCode(leg2);
		AccessToken token = polettLager.storeAccessToken(leg);
		AccessToken token2 = polettLager.storeAccessToken(leg2);
		polettLager.logOutSpiller(spiller);
		
		assertThat("Spilleren skal ikke kunne hentes med polett", polettLager.getSpillerByAccessToken(token.getAccessToken()).isPresent(), is(false));
		assertThat("Spilleren skal være logget ut", spiller.isInnlogget(),is(false));
		
	}
	
	
	@Test(expected=AutorisasjonsException.class)
	public void hvisSpillerIkkeErLoggetInnKastesException() throws Exception {
		Spiller spiller = lagTestspiller();
		
		polettLager.logOutSpiller(spiller);
	}
	
	
	


}
