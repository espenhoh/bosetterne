package com.holtebu.bosetterne.service.bosetterne.auth;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.service.bosetterne.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.bosetterne.core.AccessToken;
import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class BosetterneAuthenticator implements Authenticator<String, Spiller> {

	private Polettlager<AccessToken, Spiller, String> tokenStore;

	public BosetterneAuthenticator(
			Polettlager<AccessToken, Spiller, String> tokenStore) {
		super();
		this.tokenStore = tokenStore;
	}

	@Override
	public Optional<Spiller> authenticate(String bearer)
			throws AuthenticationException {
		return tokenStore.getSpillerByAccessToken(bearer);
	}

}