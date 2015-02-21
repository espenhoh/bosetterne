package com.holtebu.bosetterne.service.views;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.holtebu.bosetterne.api.lobby.Spill;
import com.holtebu.bosetterne.api.lobby.Spiller;

public class BosetterneView extends LobbyView {
	
	private Map<String,Spiller> innloggedeSpillere;
	
	private Set<Spill> spillListe;

	public BosetterneView(String templateName, ResourceBundle msg) {
		super(templateName, msg);
		// TODO Auto-generated constructor stub
	}

	public Map<String, Spiller> getInnloggedeSpillere() {
		return innloggedeSpillere;
	}

	public void setInnloggedeSpillere(Map<String, Spiller> innloggedeSpillere) {
		this.innloggedeSpillere = innloggedeSpillere;
	}

	public Set<Spill> getSpillListe() {
		return spillListe;
	}

	public void setSpillListe(Set<Spill> spill) {
		this.spillListe = spill;
	}

}
