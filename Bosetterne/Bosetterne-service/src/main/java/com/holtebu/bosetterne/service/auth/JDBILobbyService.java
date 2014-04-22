package com.holtebu.bosetterne.service.auth;

import org.springframework.web.filter.OncePerRequestFilter;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.yammer.dropwizard.auth.Authenticator;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class JDBILobbyService implements LobbyService<Spiller, Legitimasjon> {
	private final LobbyDAO dao;
	
	@Inject
	public JDBILobbyService(LobbyDAO dao) {
		this.dao = dao;
	}
	
	public Optional<Spiller> authenticate(BasicCredentials credentials)
			throws AuthenticationException {
		if(riktigPassord(credentials)) {
			Spiller spiller = new Spiller();
			return Optional.of(spiller);
		} else {
			return Optional.absent();
		}

	}
	
	private boolean riktigPassord(BasicCredentials credentials) {
		Legitimasjon leg = dao.finnLegVedNavn(credentials.getUsername());
		return credentials.getPassword().equals(leg.getPassord());
	}

	@Override
	public Spiller getSpiller(Legitimasjon leg) {
		// TODO Auto-generated method stub
		return null;
	}
}


