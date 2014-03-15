package com.holtebu.bosetterne.service.bosetterne.auth;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

public class BosetterneAuthenticator implements Authenticator<String, Spiller> {

	@Override
	public Optional<Spiller> authenticate(String credentials)
			throws AuthenticationException {
		if(credentials.equals("taDetRolig")) {
			Spiller spiller = new Spiller("spiller", "passord", "EPOST");
			return Optional.of(spiller);
		} else {
			return Optional.absent();
		}
	}

}
