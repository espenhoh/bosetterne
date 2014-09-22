package com.holtebu.bosetterne.service.resources.lobby;

import static org.hamcrest.CoreMatchers.equalTo;
import static com.holtebu.bosetterne.TestConst.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static com.holtebu.bosetterne.BosetterneConfigurationSuite.conf;

import java.util.HashMap;

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
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.views.HjemView;

@RunWith(JUnit4.class)
public class LoggUtResourceTest {
	
	
	private LoggUtResource res;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private OAuth2Cred auth2Cred;
	
	@BeforeClass
	public static void setUpBf() throws Exception {
		//conf = ConfigurationStub.getConf();
	}
	

	@Before
	public void setUp() throws Exception {
		auth2Cred = new OAuth2Cred(STD_CLIENTID, STD_CLIENT_SECRET);
		polettLager = new PolettlagerIMinne(new HashMap<String, Spiller>(), new HashMap<String, Legitimasjon>(), auth2Cred);
		res = new LoggUtResource(polettLager, conf);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void BosetterneConfigurationNotNull() {
		assertNotNull( "A value should've been set by a rule.", conf);
	}
	
	@Test
	public void ifPlayerNotLoggedInRedirectHome(){
		
		HjemView hjemview = res.loggUt(null);
		
		assertThat("Beskjed skal være ", assertion);
		
	}

	private void assertRedirectHome(Response response) {
		assertThat("Requesten skal ha status \"SEE_OTHER\"",response.getStatus(),is(Response.Status.SEE_OTHER.getStatusCode()));
		assertThat("Requesten skal redirecte hjem",response.getLocation().getPath(),is(equalTo("/")));
	}

	
	@Test
	public void ifPlayerLoggedInThenThePlayerMustBeRedirectedHome(){
		
		Spiller spiller = new SpillerBuilder().withBrukernavn("Testspiller").withPassord("passord").build();
		spiller.setInnlogget(true);
		
		Response response = res.loggUt(spiller);
		
		assertThat("Spiller skal være logget ut", spiller.isInnlogget(),is(false));
		assertRedirectHome(response);
	}

}
