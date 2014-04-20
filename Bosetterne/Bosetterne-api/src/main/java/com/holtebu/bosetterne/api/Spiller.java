package com.holtebu.bosetterne.api;




public class Spiller {

	public Spiller(){}
	
    public Spiller(String brukernavn) {
        this.brukernavn = brukernavn;
    }
    
    public Spiller(String brukernavn, String kallenavn, String passord, String epost) {
    	this.brukernavn = brukernavn;
    	this.kallenavn = kallenavn;
        this.passord = passord;
        this.epost = epost;
    }
	
    private String brukernavn;
    private String kallenavn;
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

	public String getKallenavn() {
		return kallenavn;
	}

	public String getPassord() {
		return passord;
	}

	public String getEpost() {
		return epost;
	}
    
}


