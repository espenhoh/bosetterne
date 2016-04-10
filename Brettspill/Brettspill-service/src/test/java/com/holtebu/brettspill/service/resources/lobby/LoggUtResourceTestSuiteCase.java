package com.holtebu.brettspill.service.resources.lobby;

import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.isA;
import static com.holtebu.bosetterne.TestConst.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static com.holtebu.bosetterne.BoardgameConfigurationSuite.conf;

import com.holtebu.brettspill.service.OAuth2Cred;
import com.holtebu.brettspill.service.auth.LobbyService;
import com.holtebu.brettspill.service.core.AccessToken;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.HashMap;
import java.util.ResourceBundle;

import javax.ws.rs.core.SecurityContext;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.BoardgameConfigurationSuite;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.api.lobby.SpillerBuilder;
import com.holtebu.brettspill.service.auth.sesjon.AutorisasjonsException;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.brettspill.service.core.Legitimasjon;
import com.holtebu.brettspill.service.views.HjemView;

/**
 * @see BoardgameConfigurationSuite
 * 
 * @author espen
 *
 */
@RunWith(JUnit4.class)
public class LoggUtResourceTestSuiteCase {
	
	
	private LoggUtResource res;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager;
	private static ResourceBundle bundle;
	private HashMap<String, Spiller> tokens;
	
	@Mock
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private OAuth2Cred auth2Cred;
	
	@BeforeClass
	public static void setUpBf() throws Exception {
		bundle = ResourceBundle.getBundle("bosetterne");
	}
	

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		auth2Cred = new OAuth2Cred(STD_CLIENTID, STD_CLIENT_SECRET);
		tokens = new HashMap<String, Spiller>();
		polettLager = new PolettlagerIMinne(tokens, new HashMap<String, Legitimasjon>(), auth2Cred);
		polettLager = spy(polettLager);
		res = new LoggUtResource(lobbyService, polettLager, conf);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void BosetterneConfigurationNotNull() {
		assertNotNull( "A value should've been set by a rule.", conf);
	}

	@Ignore
	@Test
	public void ifPlayerNotLoggedInOrDoesNotExistRedirectHomeWithExceptionalMessage(){
		
		HjemView hjemView = res.loggUt(null, bundle);
		
		String beskjed = bundle.getString("logout.userWasNotLoggedIn");
		assertThat("Beskjed skal være " + beskjed, hjemView.getBeskjed(), is(equalTo(beskjed)));
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void ifPlayerLoggedInThenThePlayerMustBeLoggedOut() throws AutorisasjonsException {
		
		Spiller spiller = new SpillerBuilder().withBrukernavn("Testspiller").withPassord("passord").build();
		spiller.setInnlogget(true);
		tokens.put("Whatever", spiller);
		SecurityContext sc = new DummySecurityContext(spiller);
		HjemView hjemview = res.loggUt(sc, bundle);
		
		
		assertThat("Spiller skal være logget ut", spiller.isInnlogget(),is(false));
		verify(polettLager).logOutSpiller(isA(Spiller.class));
		verify(lobbyService).lagreSpiller(isA(Optional.class));
		String beskjed = bundle.getString("logout.userLoggedOut");
		assertThat("Beskjed skal være " + beskjed, hjemview.getBeskjed(), is(equalTo(beskjed)));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void ifPlayerNotLoggedInThenThePlayerMustBeLoggedOut() throws AutorisasjonsException {
		
		Spiller spiller = new SpillerBuilder().withBrukernavn("Testspiller").withPassord("passord").build();
		spiller.setInnlogget(false);
		SecurityContext sc = new DummySecurityContext(spiller);
		HjemView hjemview = res.loggUt(sc, bundle);
		
		
		assertThat("Spiller skal være logget ut", spiller.isInnlogget(),is(false));
		verify(polettLager).logOutSpiller(isA(Spiller.class));
		verify(lobbyService, never()).lagreSpiller(isA(Optional.class));
		String beskjed = bundle.getString("logout.userLoggedOut");
		assertThat("Beskjed skal være " + beskjed, hjemview.getBeskjed(), is(equalTo(beskjed)));
	}
}
