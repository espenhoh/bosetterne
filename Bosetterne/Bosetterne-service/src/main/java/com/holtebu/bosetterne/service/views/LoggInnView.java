package com.holtebu.bosetterne.service.views;


import com.google.inject.Inject;
import com.holtebu.bosetterne.service.inject.names.LoggInnTemplate;

import io.dropwizard.views.View;

public class LoggInnView extends View {
	
	private boolean loggedIn = true;

	@Inject
	public LoggInnView(@LoggInnTemplate String template) {
		super(template);
        //super("/WebContent/lobby/login.mustache");
    }

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
