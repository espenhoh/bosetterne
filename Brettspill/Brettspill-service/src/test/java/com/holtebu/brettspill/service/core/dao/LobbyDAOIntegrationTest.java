package com.holtebu.brettspill.service.core.dao;

//import static org.junit.Assert.*;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.StringContains.containsString;

import com.holtebu.brettspill.api.lobby.Historikk;
import com.holtebu.brettspill.api.lobby.Spill;
import com.holtebu.brettspill.api.lobby.Spiller;

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
	public void testDuplikat() throws Exception {
		Spiller spiller = lagSpiller();
		Spiller spiller2 = lagSpiller();
		
		fjernTestSpillerHvisEksisterer(spiller.getBrukernavn());
		dao.registrerSpiller(spiller);
		try {
			dao.registrerSpiller(spiller2);
		} catch (org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException e){
			
			assertThat(e.getMessage(), containsString("Duplicate entry 'Petter' for key 'PRIMARY'"));
		}
		
		
		spiller2.setBrukernavn("NyBruker");
		try {
			dao.registrerSpiller(spiller2);
		} catch (org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException e){
			assertThat(e.getMessage(), containsString("Duplicate entry '#ff0000' for key 'farge_UNIQUE'"));
			
		}
		
		spiller2.setFarge("#444444");
		try {
			dao.registrerSpiller(spiller2);
		} catch (org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException e){
			assertThat(e.getMessage(), containsString("Duplicate entry 'Petter@pettersen.com' for key 'epost_UNIQUE'"));
		}
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
		
		long expectedTime = (System.currentTimeMillis()/1000L)*1000L;
		Timestamp sistInnlogget = new Timestamp(expectedTime);
		
		spiller.setiSpill(true);
		spiller.setInnlogget(true);
		spiller.setSistInnlogget(sistInnlogget);
		dao.oppdaterSpiller(spiller);
		Spiller hentetSpiller = dao.finnSpillerVedNavn(spiller.getBrukernavn());
		assertThat("Spiller skal være i spill", hentetSpiller.isiSpill(), is(equalTo(true)));
		assertThat("Spiller skal være innlogget", hentetSpiller.isInnlogget(), is(equalTo(true)));
		assertThat("Spiller skal være innlogget nettopp", hentetSpiller.getSistInnlogget().getTime(), is(equalTo(expectedTime)));
		
		spiller.setiSpill(false);
		spiller.setInnlogget(false);
		dao.oppdaterSpiller(spiller);
		hentetSpiller = dao.finnSpillerVedNavn(spiller.getBrukernavn());
		assertThat("Spiller skal ikke være i spill", hentetSpiller.isiSpill(), is(equalTo(false)));
		assertThat("Spiller skal ikke være innlogget", hentetSpiller.isInnlogget(), is(equalTo(false)));		
	}
	
	@Test
	public void oppdaterPassord() throws Exception {
		Spiller spiller = lagSpiller();
		
		fjernTestSpillerHvisEksisterer(spiller.getBrukernavn());
		dao.registrerSpiller(spiller);
		
		String nyttPassord = "&ØØØ#";
		spiller.setPassord(nyttPassord);
		dao.oppdaterPassord(spiller);
		Spiller hentetSpiller = dao.finnSpillerVedNavn(spiller.getBrukernavn());
		assertThat("Spiller skal ha passord: \"nyttPassord\"", hentetSpiller.getPassord(), is(equalTo(nyttPassord)));
	}
	
	@Test
	public void testHistorikk(){
		List<Historikk> historikks = dao.getHistorikk("testbruker");
		
		for (Iterator<Historikk> iterator = historikks.iterator(); iterator.hasNext();) {
			Historikk historikk = (Historikk) iterator.next();
			assertThat(historikk.getSpiller(), is("testbruker"));
		}
	}
	
	@Test
	public void testGetSpilliste(){
		List<Spill> spill = dao.getSpilliste();
		
		assertThat("Spilliste skal ha innhold", spill.isEmpty(), is(false));
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
		String testEpost = "Petter@pettersen.com";
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
