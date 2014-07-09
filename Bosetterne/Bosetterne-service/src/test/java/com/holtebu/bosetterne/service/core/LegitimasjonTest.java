package com.holtebu.bosetterne.service.core;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LegitimasjonTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testToString() throws Exception {
		String clientId = "sdgggggEEE";
		Legitimasjon leg = new Legitimasjon().setClientId(clientId);
		
		assertThat("toString skal inneholde " + clientId, leg.toString(), containsString(clientId));
	}
	

}
