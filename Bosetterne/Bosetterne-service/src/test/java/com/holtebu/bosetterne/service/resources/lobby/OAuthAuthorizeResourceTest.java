package com.holtebu.bosetterne.service.resources.lobby;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.auth.JDBILobbyService;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.auth.sesjon.PolettlagerIMinne;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.resources.lobby.OAuthAuthorizeResource;

import io.dropwizard.auth.basic.BasicCredentials;

public class OAuthAuthorizeResourceTest {

	private OAuthAuthorizeResource authResource;
	
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;
	
	@Mock
	private LobbyDAO daoMock;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		CacheLoader<String, Optional<Spiller>> loader = new CacheLoader<String, Optional<Spiller>> () {
			  public Optional<Spiller> load(String key) throws Exception {
				  return Optional.fromNullable(daoMock.finnSpillerVedNavn(key));
			  }
		};

		lobbyService = new JDBILobbyService(
			CacheBuilder.newBuilder()
				.maximumSize(1000)
				.expireAfterWrite(10, TimeUnit.MINUTES)
				.build(loader)
			);
		tokenStore = new PolettlagerIMinne(new HashMap<String, Spiller>(),new HashMap<String, Legitimasjon>(),new OAuth2Cred("id","secret"));
		authResource = new OAuthAuthorizeResource(tokenStore, lobbyService, null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogin() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

}
