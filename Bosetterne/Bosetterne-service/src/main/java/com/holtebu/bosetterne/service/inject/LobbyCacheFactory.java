package com.holtebu.bosetterne.service.inject;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.service.core.dao.LobbyDAO;

public class LobbyCacheFactory implements Factory<LoadingCache<String, Optional<Spiller>>> {
	
	private final LobbyDAO dao;

	@Inject
	public LobbyCacheFactory(LobbyDAO dao) {
		this.dao = dao;
	}

	@Override
	public LoadingCache<String, Optional<Spiller>> provide() {
		CacheLoader<String, Optional<Spiller>> loader = new CacheLoader<String, Optional<Spiller>> () {
			  public Optional<Spiller> load(String key) throws Exception {
				  return Optional.fromNullable(dao.finnSpillerVedNavn(key));
			  }
		};

		return CacheBuilder.newBuilder()
	       .maximumSize(1000)
	       .expireAfterWrite(1, TimeUnit.HOURS)
	       .build(loader);
	}

	@Override
	public void dispose(LoadingCache<String, Optional<Spiller>> instance) {
		
	}

}
