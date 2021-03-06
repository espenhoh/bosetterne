package com.holtebu.brettspill.service.resources.lobby;

import java.util.ResourceBundle;

import com.holtebu.brettspill.service.auth.BoardgameAuthenticator;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.BosetterneConfiguration;
import com.holtebu.brettspill.service.MustacheTemplates;
import com.holtebu.brettspill.service.core.dao.LobbyDAO;
import com.holtebu.brettspill.service.inject.Message;
import com.holtebu.brettspill.service.views.HjemView;
import com.holtebu.brettspill.service.views.LobbyView;

/**
 * Lobby resource (exposed at "lobby" path)
 */
@Singleton
@Path("/")
public class LobbyResource {
	static final String KLOKKE_PATTERN = "HH:mm:ss";
	
	
	private final LobbyDAO dao;
	private MustacheTemplates templates;
    private BoardgameAuthenticator authenticator;
	
	@Inject
	public LobbyResource(LobbyDAO dao, BosetterneConfiguration conf, BoardgameAuthenticator authenticator){
		this.dao = dao;
		this.templates = conf.getMustacheTemplates();
        this.authenticator = authenticator;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public HjemView hjem(@Message ResourceBundle msg) {
		return new HjemView(templates.getHjemTemplate(), msg, null);
	}

	@GET
	@Path("test")
	@Produces(MediaType.TEXT_HTML)
	public LobbyView test(@Message ResourceBundle msg) {
		return new LobbyView("/WebContent/test.mustache", msg);
	}

	@Path("logg_inn")
	public LoggInnResource loggInnResource(){
		return new LoggInnResource(templates.getLoginTemplate(), authenticator);
	}

	@Path("logg_ut")
	public Class<LoggUtResource> loggUtResource(){
		return LoggUtResource.class;
	}

	@Path("bosetterne/spill")
	public Class<GameLobbyResource> bosetterneResource(){
		return GameLobbyResource.class;
	}

	@Path("historikk")
	public Class<HistorikkResource> historikkResource() {
		return HistorikkResource.class;
	}


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{brukernavn}/secretPlan")
    public String getSecretPlan(@PathParam("brukernavn") String brukernavn, @Context SecurityContext sc) {
		Spiller spiller = (Spiller) sc.getUserPrincipal();

    	if(spiller != null && brukernavn.equals(spiller.getBrukernavn())){
    		return "Secret plan revealed!";
    	} else {
    		return "no luck";
    	}


    }


}
