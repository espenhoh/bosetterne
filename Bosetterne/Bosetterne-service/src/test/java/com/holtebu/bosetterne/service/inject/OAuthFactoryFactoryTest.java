package com.holtebu.bosetterne.service.inject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.Spiller;

public class OAuthFactoryFactoryTest {
	
	private OAuthFactoryFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = new OAuthFactoryFactory(new Authenticator<String, Spiller>() {
			
			@Override
			public Optional<Spiller> authenticate(String credentials)
					throws AuthenticationException {
				// TODO Auto-generated method stub
				return null;
			}
		}, "realm", Spiller.class);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testProvide() throws Exception {
		assertThat("", factory.provide().authenticator().authenticate("test"), is(nullValue()));
		assertThat("", factory.provide().getGeneratedClass(), is(equalTo(Spiller.class)));
	}

}
