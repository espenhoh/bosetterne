package com.holtebu.bosetterne.service.views;


import io.dropwizard.views.View;

public class RegistrerView extends View {

	private boolean loggedIn = true;

	public RegistrerView(String templateName) {
		super(templateName);
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
