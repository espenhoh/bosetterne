package com.holtebu.bosetterne.service.bosetterne.resources;

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

import com.google.common.base.Optional;
import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.holtebu.bosetterne.service.bosetterne.core.dao.LobbyDAO;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.basic.BasicCredentials;
import com.yammer.dropwizard.testing.ResourceTest;

public class LobbyResourceTest extends ResourceTest{
	
	private static LobbyDAO daoMock;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		daoMock = mock(LobbyDAO.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void registrerSpiller(){
		
	}

	@Override
	protected void setUpResources() throws Exception {
		//when(store.fetchPerson(anyString())).thenReturn(person);
        addResource(new LobbyResource(daoMock));
		
	}

}
