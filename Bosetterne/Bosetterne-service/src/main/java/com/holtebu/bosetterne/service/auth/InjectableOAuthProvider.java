package com.holtebu.bosetterne.service.auth;

import com.google.inject.Inject;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.inject.names.Realm;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.oauth.OAuthFactory;
//import io.dropwizard.auth.oauth.OAuthProvider;

public class InjectableOAuthProvider<T> {
	
	//private final OAuthFactory<T> factory;
	
	@Inject
	public InjectableOAuthProvider(
			Authenticator<String, T> authenticator,
			@Realm String realm) {
		
		//factory = new OAuthFactory<>(authenticator, realm, Spiller.class);
		
		//super(authenticator, realm);
		// TODO Auto-generated constructor stub
	}
	
	

}
