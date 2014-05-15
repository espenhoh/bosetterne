package com.holtebu.bosetterne.api;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

public class Spiller {

	public Spiller(){}
	
    public Spiller(String brukernavn, String kallenavn, String farge,
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
    private String farge;
	private String epost;
    private String passord;
    private Date datoRegistrert;
    
    private boolean innlogget;
    private boolean iSpill;
    private Set<Game> iGame;
    private Timestamp sistInnlogget;
    
    public String getPassord() {
		return passord;
	}
    
    public void setPassord(String passord) {
		this.passord = passord;
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
	
	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}

	public String getKallenavn() {
		return kallenavn;
	}

	public Set<Game> getiGame() {
		return iGame;
	}

	public Timestamp getSistInnlogget() {
		return sistInnlogget;
	}
	
	public void setSistInnlogget(Timestamp sistInnlogget) {
		this.sistInnlogget = sistInnlogget;
	}
	
	public String getFarge() {
		return farge;
	}

	public void setFarge(String farge) {
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

	void setDatoRegistrert(Date datoRegistrert) {
		this.datoRegistrert = datoRegistrert;
	}

	public boolean isiSpill() {
		return iSpill;
	}

	public void setiSpill(boolean iSpill) {
		this.iSpill = iSpill;
	}
	

    
}


