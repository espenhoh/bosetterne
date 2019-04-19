package com.holtebu.brettspill.service.auth;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.Optional;
import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.core.AccessToken;
import com.holtebu.brettspill.service.core.Legitimasjon;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class BoardgameAuthenticator implements Authenticator<String, Spiller> {

	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;

	@Inject
	public BoardgameAuthenticator(
			Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore) {
		super();
		this.tokenStore = tokenStore;
	}

	@Override
	public Optional<Spiller> authenticate(String bearer)
			throws AuthenticationException {
		return tokenStore.getSpillerByAccessToken(bearer);
	}

}
