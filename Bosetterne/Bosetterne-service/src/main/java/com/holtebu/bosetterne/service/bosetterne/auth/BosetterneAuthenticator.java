package com.holtebu.bosetterne.service.bosetterne.auth;

import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.yammer.dropwizard.auth.Authenticator;
import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class BosetterneAuthenticator implements Authenticator<BasicCredentials, Spiller> {
	@Override
	public Optional<Spiller> authenticate(BasicCredentials credentials)
			throws AuthenticationException {
		if ("espen".equals(credentials.getUsername()) && "secrets".equals(credentials.getPassword())) {
			return Optional.of(new Spiller(credentials.getUsername()));
		}
		return Optional.absent();
	}
}
