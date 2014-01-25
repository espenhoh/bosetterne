package com.holtebu.bosetterne.service.helloworld.resources;
//package com.example;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/brett")
public class MyResource {
 
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @POST
    @Path("/new")
    public Response postMsg() {
        String output = "Nytt brett opprettet";
        URI location = null;
		try {
			location = new URI("/test");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Response.created(location).entity(output).build();
    }

}