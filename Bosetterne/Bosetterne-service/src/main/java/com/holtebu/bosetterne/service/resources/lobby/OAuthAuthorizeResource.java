package com.holtebu.bosetterne.service.resources.lobby;

import io.dropwizard.auth.basic.BasicCredentials;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Singleton;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.AutorisasjonsException;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;

/**
 * Entry point for oauth authorize calls
 * 
 */
@Singleton
@Path("/authorize")
public class OAuthAuthorizeResource {
	
	private final static Logger logger = LoggerFactory.getLogger("OAuthAuthorizeResource.class");

	private Polettlager<AccessToken, Spiller ,Legitimasjon, String> tokenStore;
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	OAuth2Cred oAuth2Verdier;
	
	@Inject
	public OAuthAuthorizeResource(
			Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore,
			LobbyService<Optional<Spiller>, BasicCredentials> lobbyService,
			OAuth2Cred oAuth2Verdier) {
		this.tokenStore = tokenStore;
		this.lobbyService = lobbyService;
		this.oAuth2Verdier = oAuth2Verdier;
	}

	/*
	 * response_type Required. Must be set to token.
	 * client_id Required. The client identifier as assigned by the authorization server, when the client was registered.
	 * redirect_uri Optional. The redirect URI registered by the client.
	 * scope Optional. The possible scope of the request.
	 * state Optional (recommended). Any client state that needs to be passed on to the client request URI.
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	public Response login(
			@FormParam("username") String brukernavn,
			@FormParam("password") String passord,
			@FormParam("redirect_uri") String redirectUri,
			@FormParam("client_id") String clientId,
			@FormParam("state") String state) {
		/*
		 * Hook for implementing consent screen (which we currently don't have)
		 */

		/*
		 * From the OAuth spec:
		 * 
		 * HTTP/1.1 302 Found Location:
		 * https://client.example.com/cb?code=SplxlOBeZQQYbYS6WxSbIA &state=xyz
		 */
		
		BasicCredentials credentials = new BasicCredentials(brukernavn, passord);
		Optional<Spiller> spiller = lobbyService.getSpiller(credentials);
		
		if(!spiller.isPresent()){
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		Legitimasjon legitimasjon = new Legitimasjon().
				setClientId(clientId).
				setResponseType("code").
				setRedirectUri(redirectUri).
				setScope("read").
				setState(state).setSpiller(spiller.get());
		
		String authorizationCode;
		try {
			authorizationCode = tokenStore.storeAuthorizationCode(legitimasjon);
		} catch (AutorisasjonsException e) {
			logger.warn("Exeption ved autorisasjon ", e);
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		redirectUri = authorizationCodeRedirectURI(redirectUri, state, authorizationCode);
		return tryRedirect(redirectUri).build();
	}
	
	private String authorizationCodeRedirectURI(String redirectUri, String state, String authorizationCode) {
		String format = redirectUri.concat("?").concat("code=%s").concat("&state=%s");
		return String.format(format, authorizationCode, state);
	}

	private ResponseBuilder tryRedirect(String uri) {
		try {
			return Response.seeOther(new URI(uri));
		} catch (URISyntaxException e) {
			Exception ex = new RuntimeException(String.format("Redirect URI '%s' is not valid", uri));
			logger.warn("Noen har ikke brukt riktig URI", ex);
			return Response.status(Response.Status.UNAUTHORIZED);
		}
	}
}
