package com.holtebu.bosetterne.service.resources.lobby;

import javax.ws.rs.core.Application;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.ConfigurationStub;
import com.holtebu.bosetterne.service.MustacheTemplates;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;

import io.dropwizard.auth.basic.BasicCredentials;

public class LobbyResourceTest { // extends JerseyTest {
	
	private static LobbyDAO daoMock;
	private static BosetterneConfiguration conf;

	private LobbyResource res;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		daoMock = mock(LobbyDAO.class);
		conf = ConfigurationStub.getConf();
	}

	@Before
	public void setUp() throws Exception {
		res = new LobbyResource(daoMock, conf);
	}
	
	@Test
	public void registrerSpiller(){
		
	}
	
	@Test
	public void historikkResourceSkalReturnereHistorikkResourceClass(){
		assertThat("historikkResource skal returnere HistorikkResource.class", res.historikkResource(), sameInstance(HistorikkResource.class));
	}
	



}
