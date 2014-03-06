package com.holtebu.bosetterne.service.bosetterne.core.dao;

//import static org.junit.Assert.*;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.holtebu.bosetterne.service.bosetterne.core.dao.LobbyDAO;

public class LobbyDAOIntegrationTest{
	
	private static LobbyDAO dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DBI dbi = new DBI("jdbc:mysql://localhost:3306/bosetterne","hibernate","G6$4¤fgH5ZX");
		dao = dbi.open(LobbyDAO.class);
	}

	@AfterClass
	public static void tearDownAfterClass() {
		dao.close();
	}

	@Test
	public void testSkalFinneSpiller() throws Exception {
		Spiller spillerFunnet = dao.finnSpillerVedNavn("test_navn");
		
		assertThat("test_navn skal ha passord test_passord", spillerFunnet.getPassord(), is(equalTo("test_passord")));
	}
	
	@Test
	public void testSkalIkkeFinnePassord() throws Exception {
		Spiller spillerFunnet = dao.finnSpillerVedNavn("ikke_eksisterende_navn");
		
		assertThat("Brukeren skal ikke eksistere", spillerFunnet, is(nullValue()));
	}
	
	@Test //TODO
	public void testHenteSpiller() throws Exception {
		Spiller spillerFunnet = dao.finnSpillerVedNavn("test_navn");
		
		assertThat("test_navn skal ha passord test_passord", spillerFunnet.getPassord(), is(equalTo("test_passord")));
	}
	
	@Test
	public void settInnReturnerOgSlettSpiller() {
		String testNavn = "Petter";
		String testPassord = "Edderkopp";
		String testEpost = "test@epost.com";
		
		Spiller testSpiller = new Spiller(testNavn, testPassord, testEpost);
		
		fjernTestSpillerHvsiEksisterer(testNavn);
		
		dao.registrerSpiller(testSpiller);
		Spiller spillerFunnet = dao.finnSpillerVedNavn(testNavn);
		assertThat(testNavn + " skal ha passord " + testPassord, spillerFunnet.getPassord(), is(equalTo(testPassord)));
		
		dao.slettSpiller(testNavn);
		spillerFunnet = dao.finnSpillerVedNavn(testNavn);
		assertThat(testNavn + " skal ikke eksistere i databasen.", spillerFunnet, is(nullValue()));
	}

	private void fjernTestSpillerHvsiEksisterer(String testNavn){
		Spiller spillerFunnet = dao.finnSpillerVedNavn(testNavn);
		if(spillerFunnet != null) {
			dao.slettSpiller(testNavn);
		}
	}


}
