package com.holtebu.bosetterne.service.resources.lobby;

import java.awt.peer.LightweightPeer;

import io.dropwizard.auth.Auth;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jvnet.hk2.annotations.Service;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.views.LoggInnView;

public class LoggInnResource {
	
	static final String LOGG_INN_TEMPLATE = "/WebContent/login.mustache";
	static final String LOGGET_INN_TEMPLATE = "/WebContent/logget_inn.mustache";

	public LoggInnResource() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public LoggInnView logInn(@Auth(required = false) Spiller spiller, @Context HttpServletRequest request) {
//		Locale clientLocale = request.getLocale();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(KLOKKE_PATTERN, clientLocale);
//		String now = simpleDateFormat.format(new Date());
		if (spiller == null) {
			return new LoggInnView(LOGG_INN_TEMPLATE);
		} else {
			return new LoggInnView(LOGGET_INN_TEMPLATE);
		}
	}
	
	

}
