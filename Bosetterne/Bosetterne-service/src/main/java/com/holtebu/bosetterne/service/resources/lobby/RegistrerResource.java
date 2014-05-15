package com.holtebu.bosetterne.service.resources.lobby;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.views.RegistrerView;

@Path("/registrer")
public class RegistrerResource {
	
	//TODO legg til i YAML
	static final String registrer_template = "/WebContent/registrer.mustache";
	static final String registrert_template = "/WebContent/registrert.mustache";

	private LobbyDAO dao;

	@Inject
	public RegistrerResource(LobbyDAO dao){
		this.dao = dao;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public RegistrerView registrer(@Context HttpServletRequest request) {
		return new RegistrerView(registrer_template);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.TEXT_HTML)
	public RegistrerView registrerSpiller(
			@FormParam("brukernavn") String brukernavn,
			@FormParam("kallenavn") String kallenavn,
			@FormParam("spillerfarge") String farge,
			@FormParam("epost") String epost,
			@FormParam("passord1") String passord1,
			@FormParam("passord2") String passord2) {
		
		RegistrerView view = new RegistrerView(registrer_template);
		
		if (passord1.equals(passord2)){
			view = new RegistrerView(registrer_template);
		}
		
		Optional<Spiller> spillerFraBase = Optional.fromNullable(dao.finnSpillerVedNavn(brukernavn));
		view.setBrukernavnEksisterer(spillerFraBase.isPresent());
		
		//Spiller spiller = new Spiller(brukernavn, kallenavn, farge, epost, passord, new Date());
		
		System.out.println(brukernavn + kallenavn + farge + epost + passord1 + passord2);
		//return view;
		
		return new RegistrerView(registrert_template);
	}

}
