package com.holtebu.bosetterne.service.auth;

//import static org.junit.Assert.*;


import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

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
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.auth.JDBILobbyService;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class JDBILobbyServiceTest {
	
	private static LobbyDAO daoMock;
	
	private JDBILobbyService lobbyService;

	@BeforeClass
	public static void setUpBeforeClass(){
		daoMock = mock(LobbyDAO.class);
	}


	@Before
	public void setUp(){
		lobbyService = new JDBILobbyService(daoMock);
		
	}
	
	@Test
	public void getSpillerFinnes() {
		String username = "test";
		String password = "testPassord";
		BasicCredentials cred = new BasicCredentials(username, password);
		Spiller spillerFraDB = new Spiller(username, password, "", true, null, null);
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
		Spiller spillerFraDB = new Spiller(username, annetPassord, "", true, null, null);
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
	public void hentSpillerFraDBFeilerIkke(){
		String username = "test";
		String password = "testPassord";
		BasicCredentials cred = new BasicCredentials(username, password);
		Spiller spillerFraDB = new Spiller(username, password, "", true, null, null);
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(spillerFraDB);
		
		Spiller hentetSpiller = lobbyService.hentSpillerFraDB(cred);
		
		verify(daoMock).finnSpillerVedNavn(eq(username));
		assertThat("Spillere skal være like", hentetSpiller, is(spillerFraDB));
	}
	
	@Test
	public void riktigPassord(){
		String username = "test";
		String password = "testPassord";
		BasicCredentials cred = new BasicCredentials(username, password);
		Spiller spiller = new Spiller(username, password, "", true, null, null);
		
		boolean riktigPassord = lobbyService.riktigPassord(spiller, cred);
		
		assertThat("Passord skal være like", riktigPassord, is(equalTo(true)));
	}
	
	@Test
	public void galtPassord(){
		String username = "test";
		String password = "testPassord";
		String annetPassord = "testPassord2";
		BasicCredentials cred = new BasicCredentials(username, password);
		Spiller spiller = new Spiller(username, annetPassord, "", true, null, null);
		
		boolean riktigPassord = lobbyService.riktigPassord(spiller, cred);
		
		assertThat("Passord skal være ulike", riktigPassord, is(equalTo(false)));
	}

}
