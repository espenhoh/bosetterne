package com.holtebu.bosetterne.api.lobby;

import java.sql.Date;

public class SpillerBuilder {
	
	private Spiller spiller = new Spiller();
	
	public SpillerBuilder withBrukernavn(String brukernavn) {
		spiller.setBrukernavn(brukernavn);
		
		return this;
	}
	
	public SpillerBuilder withKallenavn(String kallenavn) {
		spiller.setKallenavn(kallenavn);
		
		return this;
	}
	
	public SpillerBuilder withPassord(String passord) {
		spiller.setPassord(passord);
		
		return this;
	}
	
	public SpillerBuilder withEpost(String epost) {
		spiller.setEpost(epost);
		
		return this;
	}
	
	public Spiller build() {
		return spiller;
	}
	
	public static Spiller lagTestspiller() {
		String testBrukernavn = "test";
		String testKallenavn = "Petter";
		String testFarge = "#ff0000";
		String testEpost = "Petter@pettersen.com";
		String testPassord = "testPassord";
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
