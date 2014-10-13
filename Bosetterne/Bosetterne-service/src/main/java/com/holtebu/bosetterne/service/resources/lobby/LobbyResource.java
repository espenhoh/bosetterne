package com.holtebu.bosetterne.service.resources.lobby;

import java.util.ResourceBundle;

import io.dropwizard.auth.Auth;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.MustacheTemplates;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.inject.Message;
import com.holtebu.bosetterne.service.views.HjemView;
import com.holtebu.bosetterne.service.views.LobbyView;
 
/**
 * Lobby resource (exposed at "lobby" path)
 */
@Path("/")
public class LobbyResource {
	static final String KLOKKE_PATTERN = "HH:mm:ss";
	
	
	private final LobbyDAO dao;
	private MustacheTemplates templates;
	
	@Inject
	public LobbyResource(LobbyDAO dao, BosetterneConfiguration conf){
		this.dao = dao;
		this.templates = conf.getMustacheTemplates();
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public HjemView hjem(@Message ResourceBundle msg, @Auth(required = false) Spiller spiller) {
		HjemView hjemView = new HjemView(templates.getHjemTemplate(), msg, spiller);
		return hjemView;
	}
	
	@GET
	@Path("test")
	@Produces(MediaType.TEXT_HTML)
	public LobbyView test(@Message ResourceBundle msg) {
		return new LobbyView("/WebContent/test.mustache", msg);
	}
	
	@Path("logg_inn")
	public LoggInnResource loggInnResource(){
		return new LoggInnResource(templates.getLoginTemplate());
	}
	
	@Path("logg_ut")
	public Class<LoggUtResource> loggUtResource(){
		return LoggUtResource.class;
	}
	
	@Path("bosetterne")
	public Class<BosetterneResource> bosetterneResource(){
		return BosetterneResource.class;
	}
	
    
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
