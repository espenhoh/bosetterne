package com.holtebu.bosetterne.service.views;




import io.dropwizard.views.View;

public class LoggInnView extends View {
	
	private boolean loggedIn = true;


	public LoggInnView( String template) {
		super(template);
    }

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
