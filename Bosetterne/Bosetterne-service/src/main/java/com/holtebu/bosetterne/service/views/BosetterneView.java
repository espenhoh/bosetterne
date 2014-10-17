package com.holtebu.bosetterne.service.views;

import java.util.Map;
import java.util.ResourceBundle;

import com.holtebu.bosetterne.api.lobby.Spiller;

public class BosetterneView extends LobbyView {
	
	private Map<String,Spiller> innloggedeSpillere;

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

}
