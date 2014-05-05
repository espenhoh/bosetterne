package com.holtebu.bosetterne.service.resources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.holtebu.bosetterne.service.views.LoginView;

@Path("/login")
@Produces(MediaType.TEXT_HTML)
public class LoginResource {
	private final String pattern = "HH:mm:ss";

	public LoginResource() {
		// TODO Auto-generated constructor stub
	}

	@GET
	public LoginView getPerson(@Context HttpServletRequest request) {
		
		Locale clientLocale = request.getLocale();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, clientLocale);
		return new LoginView(simpleDateFormat.format(new Date()));
	}

}
