package com.holtebu.brettspill.service.views.util;

public class Feilmelding {
	static final String FORAN_HTML = "<span id=\"";
	static final String MIDTEN_HTML = "\" class=\"feilmelding\"><strong>";
	static final String BAK_HTML = "</strong></span>";
	
	private final String melding;

	public Feilmelding(String id, String melding) {
		this.melding = FORAN_HTML + id + MIDTEN_HTML + melding + BAK_HTML;;
	}
	
	public String toString(){
		return melding;
	}
}
