package com.holtebu.brettspill.service.resources.lobby;

import java.sql.Date;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.holtebu.brettspill.service.inject.Message;
import com.holtebu.brettspill.service.core.dao.LobbyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.views.RegistrerView;

@Path("/registrer")
public class RegistrerResource {
	
	private final static Logger logger = LoggerFactory.getLogger("RegistrerResource.class");
	
	//TODO legg til i YAML
	static final String registrer_template = "/WebContent/registrer.mustache";
	static final String registrert_template = "/WebContent/registrert.mustache";
	static final String registrering_feilet_template = "/WebContent/registrering_feilet.mustache";

	private LobbyDAO dao;


	@Inject
	public RegistrerResource(LobbyDAO dao){
		this.dao = dao;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public RegistrerView registrer(@Context HttpServletRequest request, @Message ResourceBundle msg) {
		return new RegistrerView(registrer_template, msg);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.TEXT_HTML)
	public RegistrerView registrerSpiller(
			@Message ResourceBundle msg,
			@FormParam("brukernavn") String brukernavn,
			@FormParam("kallenavn") String kallenavn,
			@FormParam("spillerfarge") String farge,
			@FormParam("epost") String epost,
			@FormParam("passord1") String passord1,
			@FormParam("passord2") String passord2) {
		
		logger.info("Forsøker å registrere spiller {}", brukernavn);
		
		Spiller registrertSpiller = new Spiller(brukernavn, kallenavn, farge, epost, passord1, new Date(System.currentTimeMillis()));
		
		RegistrerView view = new RegistrerView(registrer_template, msg);
		
		view.setPassordMatcherIkke(!passord1.equals(passord2));
		view.setPassordForKort(passord1.length() < 6);
		
		try {
			dao.registrerSpiller(registrertSpiller);
		} catch (org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException utese) {
			view.setRegistrertSpiller(registrertSpiller);
			String feilMelding = utese.getMessage();
			if (feilMelding.contains("Duplicate entry")){
				view.setBrukernavnEksisterer(feilMelding.contains("for key 'PRIMARY'"));
				view.setEpostEksisterer(feilMelding.contains("for key 'epost_UNIQUE'"));
				view.setFargeEksisterer(feilMelding.contains("for key 'farge_UNIQUE'"));
			} else {
				logger.warn("Annen feil enn \"Duplicate entry\" ved registrering" , utese);
				utese.printStackTrace();
				return new RegistrerView(registrering_feilet_template, msg);
			}
		}
		
		if(view.feilet()) {
			return view;
		}
		
		return new RegistrerView(registrert_template, msg);
	}

}
