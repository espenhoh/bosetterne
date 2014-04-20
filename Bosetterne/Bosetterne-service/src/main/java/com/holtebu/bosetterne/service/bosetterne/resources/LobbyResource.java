package com.holtebu.bosetterne.service.bosetterne.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.holtebu.bosetterne.api.Bosetterne;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.bosetterne.core.dao.LobbyDAO;
import com.yammer.dropwizard.auth.Auth;
 
/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/lobby/")
public class LobbyResource {
	final LobbyDAO dao;
	
	@Inject
	public LobbyResource(LobbyDAO dao){
		this.dao = dao;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/logg_inn")
    public Spiller loggInn(@Auth Spiller Spiller) {
        return Spiller;
    }
	
    
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
