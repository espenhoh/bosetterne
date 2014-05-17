package com.holtebu.bosetterne.service.resources.lobby;

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
	static final String HJEM_TEMPLATE = "/WebContent/hjem.mustache";
	
	
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
