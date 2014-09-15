package com.holtebu.bosetterne.service.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An authorization filter.
 * <p>
 * If an Oauth2 token is present as a query parameter, it is moved into the header in the the Authorization field. 
 * @author espen
 *
 */
public class PolettFilter implements ContainerRequestFilter {
	
	private final static Logger logger = LoggerFactory.getLogger("PolettFilter.class");

	@Inject
	public PolettFilter() {
	}

	@Override
	/**
	 * Filters the Request Context.
	 * Requests which are not of HTTP method GET are not filtered.
	 * Requests without a token as a Queryparameter are not filtered.
	 * <p>
	 * @param ContainerRequestContext crc the Context of the general request.
	 */
	public void filter(ContainerRequestContext crc) throws IOException {
		String method = crc.getMethod();
		if(!"GET".equalsIgnoreCase(method)) {
			logger.info(method + " not filtered");
			return;
		}
		
		String token = crc.getUriInfo().getQueryParameters().getFirst("innlogget_token");
		if (token == null || token.equals("")) {
			logger.info("Spiller ikke logget inn");
			return;
		}
		
		logger.info("Putter token i header");
		String auth = "Bearer " + token;
		crc.getHeaders().add(HttpHeaders.AUTHORIZATION, auth);
		
			
	}

}
