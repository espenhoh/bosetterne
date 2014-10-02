package com.holtebu.bosetterne.service.views;

import java.util.ResourceBundle;

import com.holtebu.bosetterne.api.Spiller;

public class HjemView extends LobbyView {

	private String beskjed;
	
	public HjemView(String templateName, ResourceBundle msg, Spiller spiller) {
		super(templateName, msg);
		setSpiller(spiller);
		setBeskjed(msg.getString("home.welcome"));
	}
	
	public void setBeskjed(String beskjed) {
		this.beskjed = beskjed;
	}
	public String getBeskjed() {
		return beskjed;
	}
}
