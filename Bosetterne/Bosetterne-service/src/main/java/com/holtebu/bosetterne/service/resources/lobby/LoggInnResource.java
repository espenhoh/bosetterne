package com.holtebu.bosetterne.service.resources.lobby;

import io.dropwizard.auth.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.views.LoggInnView;

public class LoggInnResource {
	
	private final static Logger logger = LoggerFactory.getLogger("LoggInnResource.class");
	
	final String template;

	public LoggInnResource(String template) {
		this.template = template;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public LoggInnView logInn(@Auth(required = false) Spiller spiller, @Context HttpServletRequest request) {
//		Locale clientLocale = request.getLocale();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(KLOKKE_PATTERN, clientLocale);
//		String now = simpleDateFormat.format(new Date());
		
		if (spiller == null) {
			logger.info("Spiller ikke logget inn");
		} else {
			logger.info("Spiller logget inn som {}",spiller.getBrukernavn());
		}
		return new LoggInnView(template, spiller);
	}
}
