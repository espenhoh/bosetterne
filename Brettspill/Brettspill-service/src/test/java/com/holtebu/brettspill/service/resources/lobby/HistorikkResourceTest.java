package com.holtebu.brettspill.service.resources.lobby;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import com.holtebu.brettspill.service.BosetterneConfiguration;
import com.holtebu.brettspill.service.ConfigurationStub;
import com.holtebu.brettspill.service.OAuth2Cred;
import com.holtebu.brettspill.service.auth.LobbyService;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.core.dao.LobbyDAO;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.holtebu.brettspill.api.lobby.Historikk;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.api.lobby.SpillerBuilder;
import com.holtebu.brettspill.service.core.AccessToken;
import com.holtebu.brettspill.service.core.Legitimasjon;
import com.holtebu.brettspill.service.views.HistorikkView;

import javax.ws.rs.core.SecurityContext;

public class HistorikkResourceTest {
	
	private HistorikkResource res;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager;
	private static ResourceBundle bundle;
	private static BosetterneConfiguration conf;
	private HashMap<String, Spiller> tokens;
	
	@Mock
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	
	@Mock
	private LobbyDAO daoMock;
	
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
		
		res = new HistorikkResource(polettLager, conf, daoMock);
	}

	@Test
	public void ggg(){
		List<Historikk> h = new ArrayList<>();
		when(daoMock.getHistorikk(anyString())).thenReturn(h);
		Spiller spiller = SpillerBuilder.lagTestspiller();
		SecurityContext sc = new DummySecurityContext(spiller);
		HistorikkView view = res.historikk(sc, bundle);
		
		assertThat(view.getHistorikker(),sameInstance(h));
	}

}
