package com.holtebu.bosetterne.service.auth;

//import static org.junit.Assert.*;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.holtebu.bosetterne.api.lobby.Spill;
import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.api.lobby.SpillerBuilder;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;

public class JDBILobbyServiceTest {
	
	private LobbyDAO daoMock;
	private LoadingCache<String, Optional<Spiller>> spillerCache;
	private Set<Spill> spillCache;
	
	private JDBILobbyService lobbyService;
	
	private String brukernavn;
	private String passord;
	
	BasicCredentials cred;

	@Before
	public void setUp(){
		daoMock = mock(LobbyDAO.class);
		
		
		spillerCache = spy(com.holtebu.bosetterne.service.auth.JDBILobbyServiceTest.provideSpillerCache(daoMock));
		spillCache = new ConcurrentSkipListSet<>();
		
		lobbyService = new JDBILobbyService(spillerCache, spillCache, daoMock);
		
		brukernavn = "test";
		passord = "testPassord";
		
		cred = new BasicCredentials(brukernavn, passord);
	}
	
	@Test
	public void testContructor() {
		Set<Spill> spillList = new ConcurrentSkipListSet<>();
		
		List<Spill> daoList = new ArrayList<>();
		daoList.add(new Spill());
		daoList.add(new Spill());
		daoList.add(new Spill());
		
		when(daoMock.getSpilliste()).thenReturn(daoList);
		
		lobbyService = new JDBILobbyService(spillerCache, spillList, daoMock);
		
		//List<Spill> actual = lobbyService.initSpillCache(spillList);
		
		//assertThat("Listene skal inneholde det samme", spillList, is(equalTo(daoList)));
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
	
	@Test
	public void lagreSpillerSkalkalleDAO(){
		Optional<Spiller> spiller = Optional.of(testSpiller());
		
		lobbyService.lagreSpiller(spiller);
		
		verify(daoMock).oppdaterSpiller(eq(spiller.get()));
	}
	
	@Test
	public void lagreSpillerSkalIkkekalleDAO(){
		Optional<Spiller> spiller = Optional.absent();
		
		lobbyService.lagreSpiller(spiller);
		
		verify(daoMock, never() ).oppdaterSpiller(any(Spiller.class));
	}
	
	private Spiller testSpiller(){
		return new SpillerBuilder().
				withBrukernavn(brukernavn).
				withKallenavn(brukernavn).
				withPassord(passord).build();
	}
	
	public static LoadingCache<String, Optional<Spiller>> provideSpillerCache( final LobbyDAO dao){
		CacheLoader<String, Optional<Spiller>> loader = new CacheLoader<String, Optional<Spiller>> () {
			  public Optional<Spiller> load(String key) throws Exception {
				  return Optional.fromNullable(dao.finnSpillerVedNavn(key));
			  }
		};

		return CacheBuilder.newBuilder()
	       .maximumSize(1000)
	       .expireAfterWrite(1, TimeUnit.HOURS)
	       .build(loader);
	}

}
