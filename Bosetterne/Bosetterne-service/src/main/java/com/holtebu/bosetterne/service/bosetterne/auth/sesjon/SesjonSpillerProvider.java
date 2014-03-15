package com.holtebu.bosetterne.service.bosetterne.auth.sesjon;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

import com.holtebu.bosetterne.service.bosetterne.core.Spiller;

@Provider
public class SesjonSpillerProvider implements Injectable<Spiller>,
		InjectableProvider<SesjonSpiller, Type> {

	private final HttpServletRequest request;

	public SesjonSpillerProvider(@Context HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public Injectable<Spiller> getInjectable(ComponentContext cc,
			SesjonSpiller a, Type c) {
		if (c.equals(Spiller.class)) {
			return this;
		}
		return null;
	}

	@Override
	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}

	@Override
	public Spiller getValue() {
		final Spiller Spiller = (Spiller) request.getSession().getAttribute(
				"Spiller");
		if (Spiller == null) {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
		return Spiller;
	}

}
