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
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Denne implementasjonen kan hente spiller fra database via cache.
 * */
public class JDBILobbyService implements LobbyService<Optional<Spiller>, BasicCredentials> {
	
	private final static Logger logger = LoggerFactory.getLogger(JDBILobbyService.class);
	
	private final LoadingCache<String, Optional<Spiller>> spillerCache;
	
	@Inject
	public JDBILobbyService(@Named("spillerCache") LoadingCache<String, Optional<Spiller>> spillerCache) {
		this.spillerCache = spillerCache;
	}

	/**
	 * Finner spiller fra database eller cache basert på <code>BasicCredentials</code>
	 * Metoden returnerer Optional.absent når:
	 * <ul>
	 * <li> Spilleren ikke finnes </li>
	 * <li> Passord er feil </li>
	 * <li> Cache kaster ExecutionException.</li>
	 * <ul>
	 * 
	 * @param leg Brukernavn og passord til spilleren.
	 * 
	 * @return spilleren, dersom denne er registrert i databasen.
	 * 
	 */
	@Override
	public Optional<Spiller> getSpiller(BasicCredentials leg) {
		Optional<Spiller> spiller = null;
		
		try {
			spiller = hentSpillerFraCache(leg);
		} catch (ExecutionException e) {
			spiller = Optional.absent();
			logger.error("Feil ved henting av spiller",e);
		}
		
		return spiller;
	}
	
	private Optional<Spiller> hentSpillerFraCache(BasicCredentials leg) throws ExecutionException {
		Optional<Spiller> spiller = spillerCache.get(leg.getUsername());
		
		if(!rettLegitimasjon(spiller, leg)){
			spiller = Optional.absent();
		}
		
		return spiller;
	}

	private boolean rettLegitimasjon(Optional<Spiller> spiller,
			BasicCredentials leg) {
		boolean rettPassord;
		if (spiller.isPresent()){
			rettPassord = spiller.get().getPassord().equals(leg.getPassword());
		} else {
			rettPassord = false;
		}
		return rettPassord;
	}
}


