package com.holtebu.bosetterne.api;

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


import org.junit.Before;

public class SpillerTest {
	
	private Spiller spiller;

	@Before
	public void setUp() throws Exception {
		spiller = new Spiller();
	}
	
	@Test
	public void skalBliAutorisert(){
		//spiller = new Spiller("", "passord", "", true, null, null);
	}
	
	@Test
	public void skalikkeBliAutorisert(){
		//spiller = new Spiller("", "passordFeiler", "", true, null, null);
	}
	

	@After
	public void tearDown() throws Exception {
		spiller = null;
	}

}
