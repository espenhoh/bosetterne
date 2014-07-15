package com.holtebu.bosetterne.service.auth;

//import static org.junit.Assert.*;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.cache.LoadingCache;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.api.SpillerBuilder;
import com.holtebu.bosetterne.service.BosetterneModule;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;

public class JDBILobbyServiceTest {
	
	private LobbyDAO daoMock;
	private LoadingCache<String, Optional<Spiller>> spillerCache;
	
	private JDBILobbyService lobbyService;
	
	private String brukernavn;
	private String passord;
	
	BasicCredentials cred;

	@Before
	public void setUp(){
		daoMock = mock(LobbyDAO.class);
		
		BosetterneModule testModule = new BosetterneModule();
		
		spillerCache = spy(testModule.provideSpillerCache(daoMock));
		
		lobbyService = new JDBILobbyService(spillerCache);
		
		brukernavn = "test";
		passord = "testPassord";
		
		cred = new BasicCredentials(brukernavn, passord);
	}
	
	@Test
	public void testDersomSpillerMedRettLegitimasjonFinnesIDBSkalSpillerenReturneres(){
		Spiller spillerFraDatabase = testSpiller();
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(spillerFraDatabase);
		
		Optional<Spiller> spiller = lobbyService.getSpiller(cred);
		
		assertThat("Skal eksistere", spiller.isPresent(), is(equalTo(true)));
		assertThat("Spillere skal være like", spiller.get(), is(spillerFraDatabase));
	}
	
	@Test
	public void testGetSpillerFraCache(){
		Spiller spillerFraCache = testSpiller();
		spillerCache.put(cred.getUsername(), Optional.of(spillerFraCache));
		
		Optional<Spiller> spiller = lobbyService.getSpiller(cred);
		
		assertThat("Skal eksistere", spiller.isPresent(), is(equalTo(true)));
		assertThat("Spillere skal være like", spiller.get(), is(spillerFraCache));
	}
	
	@Test
	public void testDersomDBKasterExceptionSkalIngenSpillerReturneres() throws Exception {
		doThrow(new ExecutionException(new Exception("TestException"))).when(spillerCache).get(isA(String.class));
		
		Optional<Spiller> spiller = lobbyService.getSpiller(cred);
		
		assertThat("Spiller skal være absent", spiller.isPresent(), is(equalTo(false)));
	}
	
	@Test
	public void testDersomBrukernavnIkkeFinnesIDatabaseSaSkalAbsentReturneres() {
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(null);
		
		Optional<Spiller> spiller = lobbyService.getSpiller(cred);
		
		assertThat("Spiller skal være absent", spiller.isPresent(), is(equalTo(false)));
	}
	
	@Test
	public void testDersomPassordErFeilSkalAbsentReturneres() {
		Spiller spillerFraDatabase = testSpiller();
		spillerFraDatabase.setPassord("Helt feil passord");
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(spillerFraDatabase);
		
		Optional<Spiller> spiller = lobbyService.getSpiller(cred);
		
		assertThat("Spiller skal være absent", spiller.isPresent(), is(equalTo(false)));
	}
	
	private Spiller testSpiller(){
		return new SpillerBuilder().
				withBrukernavn(brukernavn).
				withKallenavn(brukernavn).
				withPassord(passord).build();
	}

}
