package com.holtebu.bosetterne.service.auth;

import javax.inject.Singleton;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

@Singleton
public class BosetterneAuthenticator implements Authenticator<String, Spiller> {

	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;

	public BosetterneAuthenticator(
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
