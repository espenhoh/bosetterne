package com.holtebu.bosetterne.service.bosetterne.auth;

import static org.junit.Assert.*;

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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.holtebu.bosetterne.service.bosetterne.core.dao.LobbyDAO;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class LobbyAuthenticatorTest {
	
	private static LobbyDAO daoMock;
	
	private LobbyAuthenticator auth;

	@BeforeClass
	public static void setUpBeforeClass(){
		daoMock = mock(LobbyDAO.class);
	}


	@Before
	public void setUp(){
		auth = new LobbyAuthenticator(daoMock);
		
	}

	@Test
	public void testShouldNotBeAuthenticated() {
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(new Spiller("42"));
		BasicCredentials credentials = new BasicCredentials("test", "test");
		Optional<Spiller> spiller;
		
		try {
			spiller = auth.authenticate(credentials);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Burde ikke få exception her da");
			return;
		}
		
		assertThat("Skal ikke bli autentisert", spiller.isPresent(), is(equalTo(false)));
	}
	
	@Test
	public void testShouldBeAuthenticated() {
		when(daoMock.finnSpillerVedNavn(isA(String.class))).thenReturn(new Spiller("test"));
		BasicCredentials credentials = new BasicCredentials("test", "test");
		Optional<Spiller> spiller;
		
		try {
			spiller = auth.authenticate(credentials);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Burde ikke få exception her da");
			return;
		}
		
		assertThat("Skal bli autentisert", spiller.isPresent(), is(equalTo(true)));
		assertThat("Skal bli autentisert", spiller.get().getNavn(), is(equalTo("test")));
	}

}
