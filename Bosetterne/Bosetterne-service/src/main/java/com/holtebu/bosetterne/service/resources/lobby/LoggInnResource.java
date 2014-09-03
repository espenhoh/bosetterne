package com.holtebu.bosetterne.service.resources.lobby;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jvnet.hk2.annotations.Service;

import com.holtebu.bosetterne.service.views.LoggInnView;


@Path("/logg_inn")
@Service
public class LoggInnResource {
	

	static final String LOGG_INN_TEMPLATE = "/WebContent/login.mustache";
	static final String LOGGET_INN_TEMPLATE = "/WebContent/logget_inn.mustache";

	@Inject
	public LoggInnResource() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public LoggInnView logInn(@Context HttpServletRequest request) {
//		Locale clientLocale = request.getLocale();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(KLOKKE_PATTERN, clientLocale);
//		String now = simpleDateFormat.format(new Date());
		return new LoggInnView(LOGG_INN_TEMPLATE);
	}
	
	

}
