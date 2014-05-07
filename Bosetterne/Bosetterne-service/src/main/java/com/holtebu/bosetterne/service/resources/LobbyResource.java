package com.holtebu.bosetterne.service.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
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

import io.dropwizard.auth.Auth;
 
/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/lobby/")
public class LobbyResource {
	private final String KLOKKE_PATTERN = "HH:mm:ss";
	
	private final LobbyDAO dao;
	private final LoggInnView loginView;
	private final HjemView hjemView;
	
	@Inject
	public LobbyResource(LobbyDAO dao, LoggInnView loginView, HjemView hjemView){
		this.dao = dao;
		this.loginView = loginView;
		this.hjemView = hjemView;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
    @Path("/logg_inn")
	public LoggInnView logInn(@Context HttpServletRequest request) {
//		Locale clientLocale = request.getLocale();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(KLOKKE_PATTERN, clientLocale);
//		String now = simpleDateFormat.format(new Date());
		return loginView;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
    @Path("/hjem")
	public HjemView hjem(@Context HttpServletRequest request) {
		return hjemView;
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
