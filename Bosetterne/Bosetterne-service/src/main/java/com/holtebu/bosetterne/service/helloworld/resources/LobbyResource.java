package com.holtebu.bosetterne.service.helloworld.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.holtebu.bosetterne.api.Bosetterne;
import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.holtebu.bosetterne.service.bosetterne.core.dao.SpillerDAO;
import com.yammer.dropwizard.auth.Auth;
 
/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/lobby")
public class LobbyResource {
	final SpillerDAO dao;
	@Inject
	public LobbyResource(SpillerDAO dao){
		this.dao = dao;
	}
}
