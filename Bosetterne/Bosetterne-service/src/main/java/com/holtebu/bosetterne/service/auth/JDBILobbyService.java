package com.holtebu.bosetterne.service.auth;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;
import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class JDBILobbyService implements LobbyService<Optional<Spiller>, BasicCredentials> {
	
	private final static Logger logger = LoggerFactory.getLogger("JDBILobbyService.class");
	
	private final LoadingCache<String, Optional<Spiller>> spillere;
	
	@Inject
	public JDBILobbyService(@Named("spillerCache") LoadingCache<String, Optional<Spiller>> spillerCache) {
		this.spillere = spillerCache;
	}

	@Override
	public Optional<Spiller> getSpiller(BasicCredentials cred) {
		Optional<Spiller> hentetSpiller = hentSpillerFraCache(cred);
		
		if(hentetSpiller.isPresent() && !riktigPassord(hentetSpiller.get(), cred)) {
			hentetSpiller = Optional.absent();
		}
		
		return hentetSpiller;
	}
	
	Optional<Spiller> hentSpillerFraCache(BasicCredentials cred) {
		try {
			return spillere.get(cred.getUsername());
		} catch (ExecutionException e) {
			e.printStackTrace();
			logger.error(e.getCause().getMessage());
			return Optional.absent();
		}
	}
	
	boolean riktigPassord(Spiller spiller, BasicCredentials credentials) {
		return credentials.getPassword().equals(spiller.getPassord());
	}
}


