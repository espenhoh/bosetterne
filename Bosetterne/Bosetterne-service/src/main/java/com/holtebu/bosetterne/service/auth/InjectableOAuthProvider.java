package com.holtebu.bosetterne.service.auth;

import com.holtebu.bosetterne.api.Spiller;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.oauth.OAuthFactory;
//import io.dropwizard.auth.oauth.OAuthProvider;

public class InjectableOAuthProvider<T> {
	

	public InjectableOAuthProvider(
			Authenticator<String, T> authenticator,
			String realm) {
		
		//factory = new OAuthFactory<>(authenticator, realm, Spiller.class);
		
		//super(authenticator, realm);
		// TODO Auto-generated constructor stub
	}
	
	

}
