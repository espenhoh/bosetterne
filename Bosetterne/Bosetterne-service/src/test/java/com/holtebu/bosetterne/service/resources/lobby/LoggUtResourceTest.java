package com.holtebu.bosetterne.service.resources.lobby;

import static org.hamcrest.CoreMatchers.equalTo;
import static com.holtebu.bosetterne.TestConst.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static com.holtebu.bosetterne.BosetterneConfigurationSuite.conf;

import java.util.HashMap;
import java.util.ResourceBundle;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.api.SpillerBuilder;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.ConfigurationStub;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.auth.sesjon.AutorisasjonsException;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.views.HjemView;

@RunWith(JUnit4.class)
public class LoggUtResourceTest {
	
	
	private LoggUtResource res;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager;
	private static ResourceBundle bundle;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private OAuth2Cred auth2Cred;
	
	@BeforeClass
	public static void setUpBf() throws Exception {
		bundle = ResourceBundle.getBundle("bosetterne");
	}
	

	@Before
	public void setUp() throws Exception {
		auth2Cred = new OAuth2Cred(STD_CLIENTID, STD_CLIENT_SECRET);
		polettLager = new PolettlagerIMinne(new HashMap<String, Spiller>(), new HashMap<String, Legitimasjon>(), auth2Cred);
		polettLager = spy(polettLager);
		res = new LoggUtResource(polettLager, conf, bundle);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void BosetterneConfigurationNotNull() {
		assertNotNull( "A value should've been set by a rule.", conf);
	}
	
	@Test
	public void ifPlayerNotLoggedInOrDoesNotExistRedirectHomeWithExceptionalMessage(){
		
		HjemView hjemView = res.loggUt(null);
		
		String beskjed = bundle.getString("logout.userWasNotLoggedIn");
		assertThat("Beskjed skal være " + beskjed, hjemView.getBeskjed(), is(equalTo(beskjed)));
		
	}

	
	@Test
	public void ifPlayerLoggedInThenThePlayerMustBeLoggedOut() throws AutorisasjonsException {
		
		Spiller spiller = new SpillerBuilder().withBrukernavn("Testspiller").withPassord("passord").build();
		spiller.setInnlogget(true);
		
		HjemView hjemview = res.loggUt(spiller);
		
		
		assertThat("Spiller skal være logget ut", spiller.isInnlogget(),is(false));
		verify(polettLager).logOutSpiller(isA(Spiller.class));
		String beskjed = bundle.getString("logout.userLoggedOut");
		assertThat("Beskjed skal være " + beskjed, hjemview.getBeskjed(), is(equalTo(beskjed)));
	}
}
