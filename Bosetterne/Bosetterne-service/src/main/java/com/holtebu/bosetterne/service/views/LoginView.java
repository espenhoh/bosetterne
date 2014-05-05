package com.holtebu.bosetterne.service.views;


import io.dropwizard.views.View;

public class LoginView extends View {
	
	private String now;

    public LoginView(String now) {
        super("/WebContent/login.mustache");
        this.now = now;
    }

    public String getNow() {
        return now;
    }
}
