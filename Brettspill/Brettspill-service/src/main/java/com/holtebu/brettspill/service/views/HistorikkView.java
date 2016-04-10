package com.holtebu.brettspill.service.views;

import java.util.List;
import java.util.ResourceBundle;

import com.holtebu.brettspill.api.lobby.Historikk;

public class HistorikkView extends LobbyView {
	private List<Historikk> historikker;
	
	public HistorikkView(String templateName, ResourceBundle msg) {
		super(templateName, msg);
		// TODO Auto-generated constructor stub
	}

	public List<Historikk> getHistorikker() {
		return historikker;
	}

	public void setHistorikker(List<Historikk> historikker) {
		this.historikker = historikker;
	}

}
