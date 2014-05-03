package com.holtebu.bosetterne.service.auth;

import com.google.inject.Inject;
import com.holtebu.bosetterne.service.inject.names.Realm;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.oauth.OAuthProvider;

public class InjectableOAuthProvider<T> extends OAuthProvider<T> {
	@Inject
	public InjectableOAuthProvider(
			Authenticator<String, T> authenticator,
			@Realm String realm) {
		super(authenticator, realm);
		// TODO Auto-generated constructor stub
	}

}
