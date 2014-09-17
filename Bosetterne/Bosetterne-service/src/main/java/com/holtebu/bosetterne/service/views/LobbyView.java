package com.holtebu.bosetterne.service.views;

import com.holtebu.bosetterne.api.Spiller;
import io.dropwizard.views.View;

public class LobbyView extends View {

	public LobbyView(String templateName) {
		super(templateName);
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
