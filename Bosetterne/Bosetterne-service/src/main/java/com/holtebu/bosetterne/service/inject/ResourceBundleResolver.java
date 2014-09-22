package com.holtebu.bosetterne.service.inject;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.eclipse.jetty.server.Request;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;

import static org.glassfish.jersey.server.model.Parameter.Source.UNKNOWN;;

@Singleton
public class ResourceBundleResolver {
	public static class MessageInjectResolver extends
			ParamInjectionResolver<Message> {

		public MessageInjectResolver() {
			super(MessageFactoryProvider.class);
			// TODO Auto-generated constructor stub
		}
	}

	public static class MessageFactoryProvider extends
			AbstractValueFactoryProvider {

		@Inject
		public MessageFactoryProvider(
				MultivaluedParameterExtractorProvider mpep,
				ServiceLocator locator) {
			super(mpep, locator, UNKNOWN);
		}

		@Override
		protected Factory<?> createValueFactory(Parameter parameter) {
			final Class<?> classType = parameter.getRawType();
			if (classType == null || !classType.equals(ResourceBundle.class)) {
				return null;
			}
			return new AbstractContainerRequestValueFactory<ResourceBundle>() {

				@Context
				HttpServletRequest request;
				
				@Override
				public ResourceBundle provide() {
					Locale locale = request.getLocale();
					ResourceBundle bundle = ResourceBundle.getBundle("bosetterne",locale);
					return bundle;
				}
				
			};
		}

	}

	public static class Binder extends AbstractBinder {
		@Override
		protected void configure() {
			bind(MessageFactoryProvider.class).to(ValueFactoryProvider.class);
			bind(MessageInjectResolver.class).to(
					new TypeLiteral<InjectionResolver<Message>>() {
					}).in(Singleton.class);
		}
	}
}
