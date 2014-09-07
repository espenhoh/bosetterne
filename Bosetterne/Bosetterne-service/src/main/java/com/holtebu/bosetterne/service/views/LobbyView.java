package com.holtebu.bosetterne.service.views;

import java.nio.charset.Charset;

import com.holtebu.bosetterne.api.Spiller;

import io.dropwizard.views.View;

public abstract class LobbyView extends View {

	protected LobbyView(String templateName) {
		super(templateName);
		// TODO Auto-generated constructor stub
	}

	private Spiller spiller = null;
	
	public boolean getInnlogget() {
		return spiller != null;
	}

	public Spiller getSpiller() {
		return spiller;
	}

	public void setSpiller(Spiller spiller) {
		this.spiller = spiller;
	}

	

}
