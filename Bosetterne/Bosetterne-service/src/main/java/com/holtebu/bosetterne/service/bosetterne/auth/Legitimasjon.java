package com.holtebu.bosetterne.service.bosetterne.auth;

public class Legitimasjon {

	public Legitimasjon(String brukernavn, String passord) {
		super();
		this.brukernavn = brukernavn;
		this.passord = passord;
	}
	
	private String brukernavn;
	private String passord;
	
	
	public String getBrukernavn() {
		return brukernavn;
	}
	public String getPassord() {
		return passord;
	}
}
