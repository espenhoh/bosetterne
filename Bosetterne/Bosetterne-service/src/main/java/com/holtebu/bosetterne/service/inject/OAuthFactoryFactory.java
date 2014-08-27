package com.holtebu.bosetterne.service.inject;

import javax.inject.Inject;
import javax.inject.Named;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.oauth.OAuthFactory;

import org.glassfish.hk2.api.Factory;

import com.holtebu.bosetterne.api.Spiller;

public class OAuthFactoryFactory implements Factory<OAuthFactory<Spiller>>{

	private String realm;
	private Class<Spiller> generatedClass;
	private Authenticator<String, Spiller> authenticator;

	@Inject
	public OAuthFactoryFactory(
			Authenticator<String,Spiller> authenticator,
            @Named("realm") String realm) {
		this.authenticator = authenticator;
        this.realm = realm;
        this.generatedClass = Spiller.class;
	}

	@Override
	public OAuthFactory<Spiller> provide() {
		// TODO Auto-generated method stub
		return new OAuthFactory<Spiller>(authenticator, realm, generatedClass);
	}

	@Override
	public void dispose(OAuthFactory<Spiller> instance) {

	}

}
