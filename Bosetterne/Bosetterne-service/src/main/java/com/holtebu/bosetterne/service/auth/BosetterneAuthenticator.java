package com.holtebu.bosetterne.service.auth;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class BosetterneAuthenticator implements Authenticator<String, Spiller> {

	private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;

	@Inject
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
