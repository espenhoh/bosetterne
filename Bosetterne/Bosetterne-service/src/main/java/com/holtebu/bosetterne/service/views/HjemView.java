package com.holtebu.bosetterne.service.views;


import com.google.inject.Inject;
import com.holtebu.bosetterne.service.inject.names.HjemTemplate;

import io.dropwizard.server.ServerFactory;
import io.dropwizard.views.View;

public class HjemView extends View {

	private boolean loggedIn = true;

	@Inject
	public HjemView(@HjemTemplate String templateName) {
		super(templateName);
		// TODO Auto-generated constructor stub
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
