package com.holtebu.bosetterne.service.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.holtebu.bosetterne.api.Bosetterne;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.views.HjemView;
import com.holtebu.bosetterne.service.views.LoggInnView;
import com.holtebu.bosetterne.service.views.RegistrerView;

import io.dropwizard.auth.Auth;
 
/**
 * Lobby resource (exposed at "lobby" path)
 */
@Path("/")
public class LobbyResource {
	static final String KLOKKE_PATTERN = "HH:mm:ss";
	static final String LOGG_INN_TEMPLATE = "/WebContent/login.mustache";
	static final String HJEM_TEMPLATE = "/WebContent/hjem.mustache";
	static final String REGISTRER_TEMPLATE = "/WebContent/registrer.mustache";
	
	private final LobbyDAO dao;
	
	@Inject
	public LobbyResource(LobbyDAO dao){
		this.dao = dao;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public HjemView hjem(@Context HttpServletRequest request) {
		return new HjemView(HJEM_TEMPLATE);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
    @Path("/registrer")
	public RegistrerView registrer(@Context HttpServletRequest request) {
		return new RegistrerView(REGISTRER_TEMPLATE);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.TEXT_HTML)
    @Path("/registrer")
	public RegistrerView registrerSpiller(
			@FormParam("brukernavn") String brukernavn,
			@FormParam("kallenavn") String kallenavn,
			@FormParam("spillerfarge") String farge,
			@FormParam("epost") String epost,
			@FormParam("passord1") String passord1,
			@FormParam("passord2") String passord2) {
		
		System.out.println(brukernavn + kallenavn + farge + epost + passord1 + passord2);
		return new RegistrerView(REGISTRER_TEMPLATE);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
    @Path("/logg_inn")
	public LoggInnView logInn(@Context HttpServletRequest request) {
//		Locale clientLocale = request.getLocale();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(KLOKKE_PATTERN, clientLocale);
//		String now = simpleDateFormat.format(new Date());
		return new LoggInnView(LOGG_INN_TEMPLATE);
	}
	
	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/logg_inn")
    public Spiller loggInn(@Auth Spiller Spiller) {
        return Spiller;
    }*/
	
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{brukernavn}/secretPlan")
    public String getSecretPlan(@PathParam("brukernavn") String brukernavn, @Auth Spiller spiller) {
    	
    	if(brukernavn.equals(spiller.getBrukernavn())){
    		return "Secret plan revealed!";
    	} else {
    		return "no luck";
    	}
    }
}
