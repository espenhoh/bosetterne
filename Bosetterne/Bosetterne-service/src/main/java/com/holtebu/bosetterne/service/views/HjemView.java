package com.holtebu.bosetterne.service.views;




import io.dropwizard.server.ServerFactory;
import io.dropwizard.views.View;

public class HjemView extends LobbyView {

	private boolean loggedIn = true;

	public HjemView(String templateName) {
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
