package com.holtebu.bosetterne.service.views;

import com.holtebu.bosetterne.api.Spiller;

public class HjemView extends LobbyView {

	public HjemView(String templateName, Spiller spiller) {
		super(templateName);
		setSpiller(spiller);
	}
}
