package com.holtebu.bosetterne.service.resources.lobby;

import com.google.common.base.Optional;
import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.service.auth.BosetterneAuthenticator;
import com.holtebu.bosetterne.service.inject.Message;
import com.holtebu.bosetterne.service.views.LoggInnView;
import io.dropwizard.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ResourceBundle;

public class LoggInnResource {
	
	private final static Logger logger = LoggerFactory.getLogger("LoggInnResource.class");
	
	final String template;
    private BosetterneAuthenticator authenticator;

	public LoggInnResource(String template, BosetterneAuthenticator authenticator) {
		this.template = template;
        this.authenticator = authenticator;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public LoggInnView logInn(@QueryParam("innlogget_token") String bearer, @Message ResourceBundle msg) {
        Optional<Spiller> spiller = null;
        try {
            spiller = authenticator.authenticate(bearer);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        if (spiller.isPresent()) {
            logger.info("Spiller logget inn som {}", spiller.get().getBrukernavn());
            spiller.get().setInnlogget(true);
		} else {
            logger.info("Spiller ikke logget inn");
		}

        return new LoggInnView(template, msg, spiller.orNull());

	}
}
