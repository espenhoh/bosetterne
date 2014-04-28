package com.holtebu.bosetterne.service.auth;


import org.springframework.web.filter.OncePerRequestFilter;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class JDBILobbyService implements LobbyService<Optional<Spiller>, BasicCredentials> {
	
	private final LobbyDAO dao;
	
	@Inject
	public JDBILobbyService(LobbyDAO dao) {
		this.dao = dao;
	}

	@Override
	public Optional<Spiller> getSpiller(BasicCredentials cred) {
		Optional<Spiller> hentetSpiller = null;
		Spiller spiller = hentSpillerFraDB(cred);
		
		if(spiller == null || !riktigPassord(spiller, cred)) {
			hentetSpiller = Optional.absent();
		} else {
			hentetSpiller = Optional.of(spiller);
		}
		
		return hentetSpiller;
	}

	Spiller hentSpillerFraDB(BasicCredentials cred) {
		return dao.finnSpillerVedNavn(cred.getUsername());
	}
	
	boolean riktigPassord(Spiller spiller, BasicCredentials credentials) {
		return credentials.getPassword().equals(spiller.getPassord());
	}
}


