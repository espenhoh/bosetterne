package com.holtebu.bosetterne.api;

import java.awt.Color;
import java.util.Date;
import java.util.Set;




public class Spiller {

	public Spiller(){}
	
    public Spiller(String brukernavn, String kallenavn, Color farge,
			String epost, String passord, Date datoRegistrert) {
		super();
		this.brukernavn = brukernavn;
		this.kallenavn = kallenavn;
		this.farge = farge;
		this.epost = epost;
		this.passord = passord;
		this.datoRegistrert = datoRegistrert;
	}

	private String brukernavn;
    private String kallenavn;
    private Color farge;
	private String epost;
    private String passord;
    private Date datoRegistrert;
    
    private boolean innlogget;
    private Set<Game> iGame;
    private Date sistInnlogget;
    
    public String getPassord() {
		return passord;
	}
    
	public boolean isInnlogget() {
		return innlogget;
	}

	public void setInnlogget(boolean innlogget) {
		this.innlogget = innlogget;
	}

	public String getBrukernavn() {
		return brukernavn;
	}

	public String getKallenavn() {
		return kallenavn;
	}

	public Set<Game> getiGame() {
		return iGame;
	}

	public Date getSistInnlogget() {
		return sistInnlogget;
	}
	
	public Color getFarge() {
		return farge;
	}

	public void setFarge(Color farge) {
		this.farge = farge;
	}

	public String getEpost() {
		return epost;
	}

	public void setEpost(String epost) {
		this.epost = epost;
	}

	public Date getDatoRegistrert() {
		return datoRegistrert;
	}

	public void setDatoRegistrert(Date datoRegistrert) {
		this.datoRegistrert = datoRegistrert;
	}
	

    
}


