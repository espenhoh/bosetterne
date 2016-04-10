package com.holtebu.brettspill.service.resources.lobby;

import com.holtebu.brettspill.service.auth.BoardgameAuthenticator;
import com.holtebu.brettspill.service.core.dao.LobbyDAO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import com.holtebu.brettspill.service.BosetterneConfiguration;
import com.holtebu.brettspill.service.ConfigurationStub;

public class LobbyResourceTest { // extends JerseyTest {
	
	private static LobbyDAO daoMock;
	private static BosetterneConfiguration conf;
    private static BoardgameAuthenticator authenticator;

	private LobbyResource res;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		daoMock = mock(LobbyDAO.class);
		conf = ConfigurationStub.getConf();
	}

	@Before
	public void setUp() throws Exception {
		res = new LobbyResource(daoMock, conf,authenticator);
	}
	
	@Test
	public void registrerSpiller(){
		
	}
	
//	@Test
//	public void historikkResourceSkalReturnereHistorikkResourceClass(){
//		assertThat("historikkResource skal returnere HistorikkResource.class", res.historikkResource(), sameInstance(HistorikkResource.class));
//	}
	



}
