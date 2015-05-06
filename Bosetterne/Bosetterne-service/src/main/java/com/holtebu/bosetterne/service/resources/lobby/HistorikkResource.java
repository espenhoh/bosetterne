package com.holtebu.bosetterne.service.resources.lobby;

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
import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.service.BosetterneConfiguration;
import com.holtebu.bosetterne.service.MustacheTemplates;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.holtebu.bosetterne.service.inject.Message;
import com.holtebu.bosetterne.service.views.HistorikkView;

public class HistorikkResource {
	private final static Logger logger = LoggerFactory.getLogger(HistorikkResource.class);

	private Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager;
	private MustacheTemplates mustacheTemplates;
	private LobbyDAO dao;
	//private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	
	
	@Inject
	public HistorikkResource(
			//LobbyService<Optional<Spiller>, BasicCredentials> lobbyService,
			Polettlager<AccessToken, Spiller, Legitimasjon, String> polettLager,
			BosetterneConfiguration conf,
			LobbyDAO dao) {
		//this.lobbyService = lobbyService;
		this.polettLager = polettLager;
		this.mustacheTemplates = conf.getMustacheTemplates();
		this.dao = dao;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public HistorikkView historikk(@Context SecurityContext sc, @Message ResourceBundle msg) {
		Spiller spiller = (Spiller) sc.getUserPrincipal();
		HistorikkView view = new HistorikkView(mustacheTemplates.getHistorikkTemplate(), msg);
		view.setSpiller(spiller);
		view.setHistorikker(dao.getHistorikk(spiller.getBrukernavn()));
		return view;
	}
}
