package com.holtebu.brettspill.service.resources.lobby;

import com.holtebu.brettspill.service.BosetterneConfiguration;
import com.holtebu.brettspill.service.MustacheTemplates;
import com.holtebu.brettspill.service.auth.LobbyService;
import com.holtebu.brettspill.service.auth.sesjon.AutorisasjonsException;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.core.AccessToken;
import com.holtebu.brettspill.service.core.Legitimasjon;
import com.holtebu.brettspill.service.inject.Message;
import com.holtebu.brettspill.service.views.HjemView;
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

public class LoggUtResource {
	private final static Logger logger = LoggerFactory.getLogger(LoggUtResource.class);
	private ResourceBundle bundle;
	private Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager;
	private MustacheTemplates mustacheTemplates;
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	
	
	@Inject
	public LoggUtResource(
			LobbyService<Optional<Spiller>, BasicCredentials> lobbyService,
			Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager,
			BosetterneConfiguration conf){
		this.lobbyService = lobbyService;
		this.polettLager = polettLager;
		this.mustacheTemplates = conf.getMustacheTemplates();
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public HjemView loggUt(@Context SecurityContext sc, @Message ResourceBundle bundle) {
		Spiller spiller = (Spiller) sc.getUserPrincipal();
		HjemView view = new HjemView(mustacheTemplates.getHjemTemplate(), bundle, spiller);
		
		if(spiller == null) {
			view.setBeskjed(bundle.getString("logout.userWasNotLoggedIn"));
		} else {
			loggUtSpiller(spiller, view);
			view.setBeskjed(bundle.getString("logout.userLoggedOut"));
		}
		
		return view;
	}

	private void loggUtSpiller(Spiller spiller, HjemView view) {
		try {
			polettLager.logOutSpiller(spiller);
			lobbyService.lagreSpiller(Optional.of(spiller));
		} catch (AutorisasjonsException e) {
			logger.debug("{} ikke logget inn", spiller);
		}
		view.setSpiller(null);
	}

}
