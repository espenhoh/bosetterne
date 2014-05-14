package com.holtebu.bosetterne.service.core.dao;

//import static org.junit.Assert.*;


import java.sql.Date;
import java.sql.Timestamp;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;

public class LobbyDAOIntegrationTest{
	
	private static LobbyDAO dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DBI dbi = new DBI("jdbc:mysql://localhost:3306/bosetterne","test","test");
		dao = dbi.open(LobbyDAO.class);
	}

	@AfterClass
	public static void tearDownAfterClass() {
		dao.close();
	}

	@Test
	public void testSkalFinneSpiller() throws Exception {
		Spiller spillerFunnet = dao.finnSpillerVedNavn("testbruker");
		
		assertThat("test_navn skal ha passord test_passord", spillerFunnet.getPassord(), is(equalTo("test_passord")));
	}
	
	@Test
	public void testSkalIkkeFinnePassord() throws Exception {
		Spiller spillerFunnet = dao.finnSpillerVedNavn("ikke_eksisterende_navn");
		
		assertThat("Brukeren skal ikke eksistere", spillerFunnet, is(nullValue()));
	}
	
	@Test //TODO
	public void settSpillerInnlogget() throws Exception {
		Spiller spillerFunnet = dao.finnSpillerVedNavn("testbruker");
		
		assertThat("test_navn skal ha passord test_passord", spillerFunnet.getPassord(), is(equalTo("test_passord")));
	}
	
	@Test
	public void oppdaterSpiller() throws Exception {
		Spiller spiller = lagSpiller();
		
		fjernTestSpillerHvisEksisterer(spiller.getBrukernavn());
		dao.registrerSpiller(spiller);
		
		spiller.setISpill(true);
		dao.oppdaterSpiller(spiller);
		Spiller hentetSpiller = dao.finnSpillerVedNavn(spiller.getBrukernavn());
		assertThat("Spiller skal være i spill", hentetSpiller.isISpill(), is(equalTo(true)));
		
		spiller.setISpill(false);
		dao.oppdaterSpiller(spiller);
		hentetSpiller = dao.finnSpillerVedNavn(spiller.getBrukernavn());
		assertThat("Spiller skal ikke være i spill", hentetSpiller.isISpill(), is(equalTo(false)));
		
		spiller.setInnlogget(true);
		dao.oppdaterSpiller(spiller);
		hentetSpiller = dao.finnSpillerVedNavn(spiller.getBrukernavn());
		assertThat("Spiller skal være innlogget", hentetSpiller.isInnlogget(), is(equalTo(true)));
		
		spiller.setInnlogget(false);
		dao.oppdaterSpiller(spiller);
		hentetSpiller = dao.finnSpillerVedNavn(spiller.getBrukernavn());
		assertThat("Spiller skal ikke være innlogget", hentetSpiller.isInnlogget(), is(equalTo(false)));
		
		Timestamp sistInnlogget = new Timestamp(System.currentTimeMillis());
		spiller.setSistInnlogget(sistInnlogget);
		dao.oppdaterSpiller(spiller);
		hentetSpiller = dao.finnSpillerVedNavn(spiller.getBrukernavn());
		//assertThat("Spiller skal være innlogget nettopp", hentetSpiller.getSistInnlogget().getTime(), is(equalTo(sistInnlogget.getTime())));
	}
	
	@Test
	public void settInnReturnerOgSlettSpiller() {

		Spiller testSpiller = lagSpiller();
		
		fjernTestSpillerHvisEksisterer(testSpiller.getBrukernavn());
		
		dao.registrerSpiller(testSpiller);
		Spiller spillerFunnet = dao.finnSpillerVedNavn(testSpiller.getBrukernavn());
		assertThat(testSpiller.getBrukernavn() + " skal ha passord " + testSpiller.getPassord(), spillerFunnet.getPassord(), is(equalTo(testSpiller.getPassord())));
		
		dao.slettSpiller(testSpiller.getBrukernavn());
		spillerFunnet = dao.finnSpillerVedNavn(testSpiller.getBrukernavn());
		assertThat(testSpiller.getBrukernavn() + " skal ikke eksistere i databasen.", spillerFunnet, is(nullValue()));
	}

	private void fjernTestSpillerHvisEksisterer(String testNavn){
		Spiller spillerFunnet = dao.finnSpillerVedNavn(testNavn);
		if(spillerFunnet != null) {
			dao.slettSpiller(testNavn);
		}
	}
	
	Spiller lagSpiller(){
		String testBrukernavn = "Petter";
		String testKallenavn = "Petter";
		String testFarge = "#ff0000";
		String testEpost = "Petter";
		String testPassord = "Edderkopp";
		Date datoReg = new Date(System.currentTimeMillis());
		
		return new Spiller(
				testBrukernavn,
				testKallenavn,
				testFarge,
				testEpost,
				testPassord,
				datoReg);
	}


}
