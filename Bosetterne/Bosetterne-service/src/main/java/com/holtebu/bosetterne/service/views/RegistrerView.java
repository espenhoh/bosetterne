package com.holtebu.bosetterne.service.views;


import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.views.util.Feilmelding;

import io.dropwizard.views.View;

public class RegistrerView extends View {
	private boolean loggedIn;
	private Feilmelding brukernavnEksisterer;
	private Feilmelding fargeEksisterer;
	private Feilmelding epostEksisterer;
	private Feilmelding passordForKort;
	private Feilmelding passordMatcherIkke;
	
	
	private Spiller registrertSpiller;

	public RegistrerView(String templateName) {
		super(templateName);
	}
	
	public boolean feilet() {
		return !(
				brukernavnEksisterer == null && 
				fargeEksisterer == null &&
				epostEksisterer == null &&
				passordForKort == null &&
				passordMatcherIkke == null);
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

	public Feilmelding getBrukernavnEksisterer() {
		return brukernavnEksisterer;
	}

	public void setBrukernavnEksisterer(boolean brukernavnEksisterer) {
		if (brukernavnEksisterer) {
			this.brukernavnEksisterer = new Feilmelding("brukernavn_fail", "Server: Brukernavnet eksisterer, velg et annet.");
		} else {
			this.brukernavnEksisterer = null;
		}
	}

	public Feilmelding getFargeEksisterer() {
		return fargeEksisterer;
	}

	public void setFargeEksisterer(boolean fargeEksisterer) {
		if (fargeEksisterer) {
			this.fargeEksisterer = new Feilmelding("farge_fail", "Server: Fargen eksisterer, velg en annen.");
		} else {
			this.fargeEksisterer = null;
		}
	}

	public Feilmelding getEpostEksisterer() {
		return epostEksisterer;
	}

	public void setEpostEksisterer(boolean epostEksisterer) {
		if (epostEksisterer) {
			this.epostEksisterer = new Feilmelding("epost_fail", "Server: Eposten eksisterer, du er fucked!");
		} else {
			this.epostEksisterer = null;
		}
	}

	public Feilmelding getPassordForKort() {
		return passordForKort;
	}

	public void setPassordForKort(boolean passordForKort) {
		if (passordForKort) {
			this.passordForKort = new Feilmelding("passord_for_kort_fail", "Server: Passordet er for kort, min 6 tegn!");
		} else {
			this.passordForKort = null;
		}
	}

	public Feilmelding getPassordMatcherIkke() {
		return passordMatcherIkke;
	}

	public void setPassordMatcherIkke(boolean passordMatcherIkke) {
		if (passordMatcherIkke) {
			this.passordMatcherIkke = new Feilmelding("passord_match_fail", "Server: Passordene er ikke like!");
		} else {
			this.passordMatcherIkke = null;
		}
	}
}
