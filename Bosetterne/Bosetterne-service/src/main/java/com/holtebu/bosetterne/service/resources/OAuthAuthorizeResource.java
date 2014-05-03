package com.holtebu.bosetterne.service.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

/**
 * Entry point for oauth authorize calls
 * 
 */
@Path("/authorize")
@Produces(MediaType.TEXT_HTML)
public class OAuthAuthorizeResource {
	
	private final static Logger logger = LoggerFactory.getLogger("OAuthAuthorizeResource.class");

	private Polettlager<AccessToken, Spiller ,Legitimasjon, String> tokenStore;
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;

	@Inject
	public OAuthAuthorizeResource(
			Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore,
			LobbyService<Optional<Spiller>, BasicCredentials> lobbyService) {
		this.tokenStore = tokenStore;
		this.lobbyService = lobbyService;
	}

	/*
	 * response_type Required. Must be set to token.
	 * client_id Required. The client identifier as assigned by the authorization server, when the client was registered.
	 * redirect_uri Optional. The redirect URI registered by the client.
	 * scope Optional. The possible scope of the request.
	 * state Optional (recommended). Any client state that needs to be passed on to the client request URI.
	 */
	@POST
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
		
		Legitimasjon legitimasjon = new Legitimasjon().
				setClientId(clientId).
				setResponseType("code").
				setRedirectUri(redirectUri).
				setScope("read").
				setState(state);
		
		String authorizationCode = tokenStore.storeAuthorizationCode(legitimasjon);

		/*
		 * Hook for implementation of Implicit Grant flow
		 */
		boolean autorisert = autorisert(spiller, authorizationCode);
		
		if (autorisert) {
			return tryRedirect(redirectUri, state, authorizationCode);
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	/**
	 * response_type 	Required. Must be set to token.
	 * client_id 	Required. The client identifier as assigned by the authorization server, when the client was registered.
	 * redirect_uri 	Optional. The redirect URI registered by the client.
	 * scope 	Optional. The possible scope of the request.
	 * state 	Optional (recommended). Any client state that needs to be passed on to the client request URI.
	 **/
	@POST
	@Path("/implicit")
	public Response loginImplicit(
			@FormParam("username") String brukernavn,
			@FormParam("password") String passord,
			@FormParam("redirect_uri") String redirectUri,
			@FormParam("client_id") String clientId,
			@FormParam("state") String state, 
			@FormParam("reponse_type")String responseType) {
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
		
		Legitimasjon legitimasjon = new Legitimasjon().
				setClientId(clientId).
				setResponseType("code").
				setRedirectUri(redirectUri).
				setScope("read").
				setState(state);
		
		String authorizationCode = tokenStore.storeAuthorizationCode(legitimasjon);

		/*
		 * Hook for implementation of Implicit Grant flow
		 */
		boolean autorisert = autorisert(spiller, authorizationCode);
		
		if (autorisert) {
			return tryRedirect(redirectUri, state, authorizationCode);
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	private Response tryRedirect(String redirectUri, String state, String authorizationCode) {
		String format = redirectUri.concat("?").concat("code=%s").concat("&state=%s");
		String uri = String.format(format, authorizationCode, state);
		try {
			return Response.seeOther(new URI(uri)).build();
		} catch (URISyntaxException e) {
			Exception ex = new RuntimeException(String.format("Redirect URI '%s' is not valid", uri));
			logger.warn("Noen har ikke brukt riktig URI", ex);
			return null;
		}
	}

	private boolean autorisert(Optional<Spiller> spiller,
			String authorizationCode) {
		return spiller.isPresent() && authorizationCode != null;
	}
}
