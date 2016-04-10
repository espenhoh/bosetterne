package com.holtebu.brettspill.service.resources.lobby;

import com.holtebu.brettspill.service.BosetterneConfiguration;
import com.holtebu.brettspill.service.MustacheTemplates;
import com.holtebu.brettspill.service.auth.LobbyService;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.core.AccessToken;
import com.holtebu.brettspill.service.core.Legitimasjon;
import com.holtebu.brettspill.service.inject.Message;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.views.BosetterneView;

public class BosetterneResource {
	private final static Logger logger = LoggerFactory.getLogger(BosetterneResource.class);

	private Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager;
	private MustacheTemplates mustacheTemplates;
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	
	
	@Inject
	public BosetterneResource(
			LobbyService<Optional<Spiller>, BasicCredentials> lobbyService,
			Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager,
			BosetterneConfiguration conf) {
		this.lobbyService = lobbyService;
		this.polettLager = polettLager;
		this.mustacheTemplates = conf.getMustacheTemplates();
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public BosetterneView bosetterne(@Context SecurityContext sc, @Message ResourceBundle msg){
		BosetterneView view = new BosetterneView(mustacheTemplates.getBosetterneTemplate(), msg);
		if (sc.isUserInRole("Spiller")){
			view.setSpiller((Spiller)sc.getUserPrincipal());
		}

		view.setInnloggedeSpillere(polettLager.getInnloggedeSpillere());
		view.setSpillListe(lobbyService.hentListe());
		return view;
	}

}
