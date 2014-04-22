package com.holtebu.bosetterne.service.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.springframework.web.filter.OncePerRequestFilter;

public class Sikkerhetsfilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,	HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String url = request.getRequestURL().toString();
		String accessToken = request.getHeader("Authorization");
		try {
			if (accessToken == null || accessToken.isEmpty()) {
				throw new Exception(
						"Code " + Status.UNAUTHORIZED.getStatusCode() +
						"\nIngen adgang, manglende tilgangspolett."
								+ accessToken);
			}
			if (url.endsWith("/signin")) {
				// Don't Do anything
				filterChain.doFilter(request, response);
			} else {
				// AUTHORIZE the access_token here. If authorization goes
				// through, continue as normal, OR throw a 401 unaurhtorized
				// exception

				filterChain.doFilter(request, response);
			}
		} catch (Exception ex) {
			response.setStatus(401);
			response.setCharacterEncoding("UTF-8");
			response.setContentType(MediaType.APPLICATION_JSON);
			response.getWriter().print("Unauthorized");
		}

	}

}