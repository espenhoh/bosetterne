package com.holtebu.bosetterne.service.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource. Redirects home :)
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class RedirectResource {
	public RedirectResource() {}
	
	@GET
	public Response redirect() {
		try {
			return Response.seeOther(new URI("hjem")).build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.serverError().build();
	}
}
