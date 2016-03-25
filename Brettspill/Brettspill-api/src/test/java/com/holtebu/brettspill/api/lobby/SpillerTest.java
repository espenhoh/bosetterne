package com.holtebu.brettspill.api.lobby;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpillerTest {
	
	private Spiller testSpiller;
	
	private String brukernavn;

	@Before
	public void setUp() throws Exception {
		brukernavn = "brukernavn";
		testSpiller = new SpillerBuilder().withBrukernavn(brukernavn).build();
	}
	
	@Test
	public void spillereSkalVareLikeNarBrukernavnErLikt() throws Exception {
		Spiller testSpiller2 = new SpillerBuilder().withBrukernavn(brukernavn).build();
		assertThat("Spillere skal være like", testSpiller.equals(testSpiller2), is(equalTo(true)));
	}
	
	@Test
	public void spillereSkalVareUlikeNarBrukernavnErUlikt() throws Exception {
		Spiller testSpiller2 = new SpillerBuilder().withBrukernavn("tullenavn").build();
		assertThat("Spillere skal være ulike når brukernavn er ulikt", testSpiller.equals(testSpiller2), is(equalTo(false)));
	}
	
	@Test
	public void spillereSkalVareUlikeNarSpiller2ErNull() throws Exception {
		Spiller testSpiller2 = null;
		assertThat("Spillere skal være ulike når spiller 2 er null", testSpiller.equals(testSpiller2), is(equalTo(false)));
	}
	
	@Test
	public void spillereSkalVareUlikeNarSpiller2IkkeErSpiller() throws Exception {
		Object testSpiller2 = new Object();
		assertThat("Spillere skal være ulike når det ene objektet ikke er en spiller", testSpiller.equals(testSpiller2), is(equalTo(false)));
	}
	
	@Test
	public void testHashCode() throws Exception {
		assertThat("HashCode skal være samme som brukernavnets hashCode", testSpiller.hashCode(), is(equalTo(brukernavn.hashCode())));
	}
	
	@Test
	public void testHashCodeNarBrukernavnNullLik0() throws Exception {
		testSpiller.setBrukernavn(null);
		assertThat("HashCode skal være 0 når brukernavn er null", testSpiller.hashCode(), is(equalTo(0)));
	}
	
	/*
	@Test
	public void skalBliAutorisert(){
		//spiller = new Spiller("", "passord", "", true, null, null);
	}
	
	@Test
	public void skalikkeBliAutorisert(){
		//spiller = new Spiller("", "passordFeiler", "", true, null, null);
	}*/
	

	@After
	public void tearDown() throws Exception {
		testSpiller = null;
	}

}
