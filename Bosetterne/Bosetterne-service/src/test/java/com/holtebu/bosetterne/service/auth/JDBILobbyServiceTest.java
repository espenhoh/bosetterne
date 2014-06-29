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
	
	private String username;
	private String password;

	@Before
	public void setUp(){
		daoMock = mock(LobbyDAO.class);
		
		BosetterneModule testModule = new BosetterneModule(mock(BosetterneConfiguration.class), mock(DBI.class));
		
		spillerCache = spy(testModule.provideSpillerCache(daoMock));
		
		lobbyService = new JDBILobbyService(
				testModule.provideSpillerCache(daoMock)
				);
		
		username = "test";
		password = "testPassord";
	}
	
	@Test
	public void testDersomSpillerMedRettLegitimasjonFinnesIDBSkalSpillerenReturneres(){
		BasicCredentials cred = new BasicCredentials(username, password);
		Spiller spillerFraDatabase = new SpillerBuilder().
				withBrukernavn(username).
				withKallenavn(username).
				withPassord(password).build();
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(spillerFraDatabase);
		
		Optional<Spiller> spiller = lobbyService.getSpiller(cred);
		
		assertThat("Skal eksistere", spiller.isPresent(), is(equalTo(true)));
		assertThat("Spillere skal være like", spiller.get(), is(spillerFraDatabase));
	}
	
	@Test
	public void testDersomDBKasterExceptionSkalIngenSpillerReturneres() throws Exception {
		BasicCredentials cred = new BasicCredentials(username, password);
		
		when(spillerCache.get(isA(String.class))).thenThrow(new ExecutionException(new Exception()));
		//doThrow(ExecutionException.class).when(spillerCache).get(isA(String.class));
		
		Optional<Spiller> spiller = lobbyService.getSpiller(cred);
		
		assertThat("Spiller skal være absent", spiller.isPresent(), is(equalTo(false)));
	}
	
	/*
	@Test
	public void getSpillerFinnes() {
		String username = "test";
		String password = "testPassord";
		BasicCredentials cred = new BasicCredentials(username, password);
		Spiller spillerFraDB = new Spiller(username, username, "", "", password, null);
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(spillerFraDB);
		
		Optional<Spiller> spiller = lobbyService.getSpiller(cred);
		
		assertThat("Skal eksistere", spiller.isPresent(), is(equalTo(true)));
		assertThat("Spillere skal være like", spiller.get(), is(spillerFraDB));
	}
	
	@Test
	public void getSpillerPassordFeil() {
		String username = "test";
		String password = "testPassord";
		String annetPassord = "testPassord2";
		BasicCredentials cred = new BasicCredentials(username, password);
		Spiller spillerFraDB = new Spiller(username, username, "", "", annetPassord, null);
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(spillerFraDB);
		
		Optional<Spiller> spiller = lobbyService.getSpiller(cred);
		
		assertThat("Skal ikke returneres", spiller.isPresent(), is(equalTo(false)));
	}
	
	@Test
	public void getSpillerFinnesIkke() {
		String username = "Ikkeeksisterende";
		String password = "testspiller";
		BasicCredentials cred = new BasicCredentials(username, password);
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(null);
		
		Optional<Spiller> spiller = lobbyService.getSpiller(cred);
		
		assertThat("Skal eksistere", spiller.isPresent(), is(equalTo(false)));
	}
	
	@Test
	public void hentSpillerFraCache(){
		
		String username = "test";
		String password = "testPassord";
		verify(daoMock, Mockito.times(0)).finnSpillerVedNavn(eq(username));
		BasicCredentials cred = new BasicCredentials(username, password);
		Spiller spillerFraDB = new Spiller(username, username, "", "", password, null);
		
		Optional<Spiller> hentetSpiller = lobbyService.hentSpillerFraCache(new BasicCredentials("skal ikke", "finnes"));
		assertThat("Spiller skal ikke finnes", hentetSpiller.isPresent(), is(equalTo(false)));
		
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(spillerFraDB);
		
		for (int i = 0; i < 6; i++) {
			hentetSpiller = lobbyService.hentSpillerFraCache(cred);
			assertThat("Spiller skal finnes", hentetSpiller.isPresent(), is(equalTo(true)));
			assertThat("Spillere skal være like", hentetSpiller.get(), is(spillerFraDB));
		}

		verify(daoMock, Mockito.times(1)).finnSpillerVedNavn(eq(username));
	}
	
	@Test
	public void riktigPassord(){
		String username = "test";
		String password = "testPassord";
		BasicCredentials cred = new BasicCredentials(username, password);
		Spiller spiller = new Spiller(username, username, "", "", password, null);
		
		boolean riktigPassord = lobbyService.riktigPassord(spiller, cred);
		
		assertThat("Passord skal være like", riktigPassord, is(equalTo(true)));
	}
	
	@Test
	public void galtPassord(){
		String username = "test";
		String password = "testPassord";
		String annetPassord = "testPassord2";
		BasicCredentials cred = new BasicCredentials(username, password);
		Spiller spiller = new Spiller(username, username, "", "", annetPassord, null);
		
		boolean riktigPassord = lobbyService.riktigPassord(spiller, cred);
		
		assertThat("Passord skal være ulike", riktigPassord, is(equalTo(false)));
	}*/

}
