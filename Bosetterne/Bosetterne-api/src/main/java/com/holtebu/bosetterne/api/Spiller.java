package com.holtebu.bosetterne.api;

import java.util.Date;
import java.util.Set;




public class Spiller {

	public Spiller(){}
    
    public Spiller(String brukernavn, String kallenavn, boolean innlogget,
			Set<Game> iGame, Date sistInnlogget) {
		super();
		this.brukernavn = brukernavn;
		this.kallenavn = kallenavn;
		this.innlogget = innlogget;
		this.iGame = iGame;
		this.sistInnlogget = sistInnlogget;
	}
	
    private String brukernavn;
    private String kallenavn;
    private boolean innlogget;
    private Set<Game> iGame;
    private Date sistInnlogget;
    
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

    
}


