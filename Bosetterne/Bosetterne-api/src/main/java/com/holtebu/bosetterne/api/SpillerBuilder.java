package com.holtebu.bosetterne.api;

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

}
