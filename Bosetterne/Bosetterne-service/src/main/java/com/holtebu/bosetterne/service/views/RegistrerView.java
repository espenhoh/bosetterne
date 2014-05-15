package com.holtebu.bosetterne.service.views;


import com.holtebu.bosetterne.api.Spiller;

import io.dropwizard.views.View;

public class RegistrerView extends View {
	private static final String BRUKERNAVN_EKSISTERER_SPAN = "<span id=\"brukernavn_fail\" class=\"feilmelding\" hidden><strong>Brukernavnet eksisterer, velg et annet.</strong></span>";
	private static final String FARGE_EKSISTERER_SPAN = "<span id=\"farge_fail\" class=\"feilmelding\" hidden><strong>Fargen eksisterer, velg en annen.</strong></span>";
	private static final String EPOST_EKSISTERER_SPAN = "<span id=\"epost_fail\" class=\"feilmelding\" hidden><strong>Eposten eksisterer, du er fucked!</strong></span>";
	
	private boolean loggedIn;
	private String brukernavnEksisterer;
	private String fargeEksisterer;
	private String epostEksisterer;
	
	private Spiller registrertSpiller;

	public RegistrerView(String templateName) {
		super(templateName);
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public boolean isRegistrert(){
		return registrertSpiller != null;
	}

	public Spiller getRegistrertSpiller() {
		return registrertSpiller;
	}

	public void setRegistrertSpiller(Spiller registrertSpiller) {
		this.registrertSpiller = registrertSpiller;
	}

	public String getBrukernavnEksisterer() {
		return brukernavnEksisterer;
	}

	public void setBrukernavnEksisterer(boolean brukernavnEksisterer) {
		if (brukernavnEksisterer) {
			this.brukernavnEksisterer = BRUKERNAVN_EKSISTERER_SPAN;
		} else {
			this.brukernavnEksisterer = null;
		}
	}

	public String getFargeEksisterer() {
		return fargeEksisterer;
	}

	public void setFargeEksisterer(boolean fargeEksisterer) {
		if (fargeEksisterer) {
			this.fargeEksisterer = FARGE_EKSISTERER_SPAN;
		} else {
			this.fargeEksisterer = null;
		}
	}

	public String getEpostEksisterer() {
		return epostEksisterer;
	}

	public void setEpostEksisterer(boolean epostEksisterer) {
		if (epostEksisterer) {
			this.brukernavnEksisterer = EPOST_EKSISTERER_SPAN;
		} else {
			this.brukernavnEksisterer = null;
		}
	}
}
