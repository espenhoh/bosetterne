package com.holtebu.bosetterne.service.resources.lobby;


import static com.holtebu.bosetterne.TestConst.STD_CLIENTID;
import static com.holtebu.bosetterne.TestConst.STD_CLIENT_SECRET;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.api.SpillerBuilder;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.ConfigurationStub;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.views.BosetterneView;

public class BosetterneResourceTest {
	private BosetterneResource res;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager;
	private static ResourceBundle bundle;
	private static BosetterneConfiguration conf;
	private HashMap<String, Spiller> tokens;
	
	@Mock
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private OAuth2Cred auth2Cred;
	
	@BeforeClass
	public static void setUpBf() throws Exception {
		bundle = ResourceBundle.getBundle("bosetterne");
		conf = ConfigurationStub.getConf();
	}
	

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		auth2Cred = new OAuth2Cred(STD_CLIENTID, STD_CLIENT_SECRET);
		tokens = new HashMap<String, Spiller>();
		polettLager = new PolettlagerIMinne(tokens, new HashMap<String, Legitimasjon>(), auth2Cred);
		polettLager = spy(polettLager);
		res = new BosetterneResource(lobbyService, polettLager, conf);
	}

	@Test
	public void skalSetteSpillerPaaView(){
		Spiller spiller = SpillerBuilder.lagTestspiller();
		
		BosetterneView view = res.bosetterne(spiller, bundle);
		
		assertThat("Spiller skal være på view",view.getSpiller(), is(sameInstance(spiller)));
	}
	
	@Test
	public void skalSetteInnloggedeSpillerePaaView(){
		Spiller spiller = SpillerBuilder.lagTestspiller();
		
		BosetterneView view = res.bosetterne(spiller, bundle);
		
		assertThat("Innloggede Spillere skal være på view", view.getInnloggedeSpillere(), is(sameInstance(polettLager.getInnloggedeSpillere())));
		
	}
}
