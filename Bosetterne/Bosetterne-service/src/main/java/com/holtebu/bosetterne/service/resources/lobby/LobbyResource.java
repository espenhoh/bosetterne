package com.holtebu.bosetterne.service.resources.lobby;

import io.dropwizard.auth.Auth;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.views.HjemView;
 
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
	public HjemView hjem(@Auth(required = false) Spiller spiller) {
		//public HjemView hjem(@Context HttpServletRequest request) {
		HjemView hjemView = new HjemView(HJEM_TEMPLATE);
		hjemView.setSpiller(spiller);
		return hjemView;
	}
	
	@Path("logg_inn")
	public LoggInnResource loggInnResource(){
		return new LoggInnResource();
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
    	
    	Object[] f = {,};
    	if(brukernavn.equals(spiller.getBrukernavn())){
    		return "Secret plan revealed!";
    	} else {
    		return "no luck";
    	}
    	
        
    }
}
