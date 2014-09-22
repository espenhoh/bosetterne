package com.holtebu.bosetterne.service.views;

import java.util.ResourceBundle;

import com.holtebu.bosetterne.api.Spiller;

public class LoggInnView extends LobbyView {

	public LoggInnView(String template, ResourceBundle msg, Spiller spiller) {
		super(template, msg);
		setSpiller(spiller);
    }
}
