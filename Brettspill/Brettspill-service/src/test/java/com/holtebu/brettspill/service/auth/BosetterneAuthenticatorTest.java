package com.holtebu.brettspill.service.auth;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.holtebu.bosetterne.BosetterneConfigurationSuite.conf;

import java.util.HashMap;

import com.holtebu.brettspill.service.OAuth2Cred;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.brettspill.service.core.AccessToken;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Optional;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.api.lobby.SpillerBuilder;
import com.holtebu.brettspill.service.core.Legitimasjon;

public class BosetterneAuthenticatorTest {
	
	private static OAuth2Cred oAuth2Verdier;
	private BosetterneAuthenticator authenticator;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
        oAuth2Verdier = conf.getOauth2();
	}

	@Before
	public void setUp() throws Exception {
		HashMap<String, Spiller> accessTokens = new HashMap<String, Spiller>();
		HashMap<String, Legitimasjon> codes = new HashMap<String, Legitimasjon>();
		tokenStore = new PolettlagerIMinne(accessTokens, codes, oAuth2Verdier);
		
		authenticator = new BosetterneAuthenticator(tokenStore);
	}
	
	@Test
	public void testAuthenticated() throws Exception{
		Spiller spiller = new SpillerBuilder().withBrukernavn("test").withKallenavn("Petter").withEpost("Petter@pettersen.com").withPassord("testPassord").build();
		Legitimasjon leg = new Legitimasjon().setClientId(oAuth2Verdier.getClientId()).setSecret(oAuth2Verdier.getClientSecret()).setSpiller(spiller);
		AccessToken token = tokenStore.storeAccessToken(leg);
		
		Optional<Spiller> hentetSpiller = authenticator.authenticate(token.getAccessToken());
		
		assertThat("Spiller skal eksistere", hentetSpiller.isPresent(),is(true));
		assertThat("Spiller skal være samme", hentetSpiller.get(),is(spiller));
	}
	
	@Test
	public void testNotAuthenticated() throws Exception{
		Optional<Spiller> hentetSpiller = authenticator.authenticate("Dette feiler!");
		
		assertThat("Spiller skal ikke eksistere", hentetSpiller.isPresent(),is(false));
	}

}
