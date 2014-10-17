package com.holtebu.bosetterne.service.resources.lobby;

import io.dropwizard.auth.Auth;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.MustacheTemplates;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.AutorisasjonsException;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.inject.Message;
import com.holtebu.bosetterne.service.views.HjemView;

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
	public HjemView loggUt(@Auth(required = false) Spiller spiller, @Message ResourceBundle bundle) {
		
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
