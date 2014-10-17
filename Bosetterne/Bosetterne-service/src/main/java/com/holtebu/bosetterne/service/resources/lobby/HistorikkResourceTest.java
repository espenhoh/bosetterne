package com.holtebu.bosetterne.service.resources.lobby;

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

import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.api.lobby.SpillerBuilder;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.ConfigurationStub;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.views.HistorikkView;

public class HistorikkResourceTest {
	
	private HistorikkResource res;
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
		res = new HistorikkResource(polettLager, conf);
	}

	@Test
	public void ggg(){
		Spiller spiller = SpillerBuilder.lagTestspiller();
		
		HistorikkView view = res.historikk(spiller, bundle);
	}

}
