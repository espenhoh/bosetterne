package com.holtebu.bosetterne.service.resources;

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
import com.holtebu.bosetterne.api.Spiller;
 
/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/brett")
public class MyResource {
	
	private final Bosetterne bosetterne;
	
	private String getit;
	
	@Inject
	public MyResource(Bosetterne bosetterne, @Named("getit") String getit){
		this.bosetterne = bosetterne;
		this.getit = getit;
	}
 
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return getit;
    }

    

    @PUT
    @Path("/putIt")
    public void putIt() {
    	getit = "putIt";
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