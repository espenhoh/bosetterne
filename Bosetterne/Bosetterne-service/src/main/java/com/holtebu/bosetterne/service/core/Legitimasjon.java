package com.holtebu.bosetterne.service.core;

/** Håndterer innlogging og sesjon */
public class Legitimasjon {


	public Legitimasjon(String brukernavn, String passord, String epost,
			String code) {
		super();
		this.brukernavn = brukernavn;
		this.passord = passord;
		this.epost = epost;
		this.code = code;
	}
	
	private String brukernavn;
	private String passord;
    private String epost;
    private String code;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBrukernavn() {
		return brukernavn;
	}
	public String getPassord() {
		return passord;
	}
}
