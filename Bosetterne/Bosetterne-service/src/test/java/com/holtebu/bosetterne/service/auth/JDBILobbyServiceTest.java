package com.holtebu.bosetterne.service.auth;

//import static org.junit.Assert.*;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skife.jdbi.v2.DBI;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.spy;
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
import com.google.common.cache.LoadingCache;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.api.SpillerBuilder;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.BosetterneModule;
import com.holtebu.bosetterne.service.auth.JDBILobbyService;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;

import io.dropwizard.auth.basic.BasicCredentials;

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
