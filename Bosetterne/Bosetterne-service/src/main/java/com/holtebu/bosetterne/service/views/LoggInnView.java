package com.holtebu.bosetterne.service.views;

import com.holtebu.bosetterne.api.Spiller;

public class LoggInnView extends LobbyView {

	public LoggInnView(String template, Spiller spiller) {
		super(template);
		setSpiller(spiller);
    }
}
