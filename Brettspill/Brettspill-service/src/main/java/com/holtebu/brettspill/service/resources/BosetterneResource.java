package com.holtebu.brettspill.service.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.holtebu.brettspill.api.Bosetterne;
import com.holtebu.brettspill.api.components.boardcomponents.AxialHexCoordinates;
import com.holtebu.brettspill.api.components.boardcomponents.Hex;
import com.holtebu.brettspill.api.components.boardcomponents.HexCoordinates;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/brett")
public class BosetterneResource {
	
	private final Bosetterne bosetterne;
	
	private String getit;
	
	@Inject
	public BosetterneResource(Bosetterne bosetterne,  String getit){
		this.bosetterne = bosetterne;
		this.getit = getit;
	}


    @GET
    @Path("/hex")
    @Produces(MediaType.APPLICATION_JSON)
    public Hex sayHex() {
        HexCoordinates coordinates = new AxialHexCoordinates(1,2);
        return new Hex(coordinates);
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