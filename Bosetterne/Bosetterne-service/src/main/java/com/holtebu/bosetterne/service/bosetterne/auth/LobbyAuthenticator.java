package com.holtebu.bosetterne.service.bosetterne.auth;

import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.holtebu.bosetterne.service.bosetterne.core.dao.LobbyDAO;
import com.yammer.dropwizard.auth.Authenticator;
import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class LobbyAuthenticator implements Authenticator<BasicCredentials, Spiller> {
	private final LobbyDAO dao;
	
	public LobbyAuthenticator(LobbyDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public Optional<Spiller> authenticate(BasicCredentials credentials)
			throws AuthenticationException {
		if(riktigPassord(credentials)) {
			Spiller spiller = new Spiller(credentials.getUsername(), credentials.getPassword(), "EPOST");
			return Optional.of(spiller);
		} else {
			return Optional.absent();
		}

	}
	
	private boolean riktigPassord(BasicCredentials credentials) {
		Spiller spiller = dao.finnSpillerVedNavn(credentials.getUsername());
		return credentials.getPassword().equals(spiller.getPassord());
	}
}
