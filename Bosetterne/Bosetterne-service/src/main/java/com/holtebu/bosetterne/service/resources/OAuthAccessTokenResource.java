package com.holtebu.bosetterne.service.resources;

import io.dropwizard.auth.basic.BasicCredentials;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.OAuth2Cred;
import com.holtebu.bosetterne.service.auth.LobbyService;
import com.holtebu.bosetterne.service.auth.sesjon.AutorisasjonsException;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.google.common.base.Optional;
import com.google.inject.Inject;

//import com.yammer.dropwizard.logging.Log;

/**
 * Entry point for oauth calls
 * 
 */
@Path("/token")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class OAuthAccessTokenResource {
	
	private final static Logger logger = LoggerFactory.getLogger("OAuthAccessTokenResource.class");

	private final Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;
	private LobbyService<Optional<Spiller>, BasicCredentials> lobbyService;
	private OAuth2Cred oAuth2Verdier;

	// private static final Log LOG =
	// Log.forClass(OAuthAccessTokenResource.class);

	@Inject
	public OAuthAccessTokenResource(
			Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore,
			LobbyService<Optional<Spiller>, BasicCredentials> lobbyService,
			OAuth2Cred oAuth2Verdier) {
		super();
		this.tokenStore = tokenStore;
		this.lobbyService = lobbyService;
		this.oAuth2Verdier = oAuth2Verdier;
	}

	/**
	client_id 	Required. The client application's id.
	client_secret 	Required. The client application's client secret .
	grant_type 	Required. Must be set to authorization_code .
	code 	Required. The authorization code received by the authorization server.
	redirect_uri 	Required, if the request URI was included in the authorization request. Must be identical then.*/
	@POST
	public AccessToken accessToken(
			@HeaderParam("Authorization") String authorization,
			@FormParam("client_id") String clientID,
			@FormParam("client_secret") String clientSecret,
			@FormParam("grant_type") String grantType,
			@FormParam("code") String code,
			@FormParam("redirect_uri") String redirectUri) {
		/*
		 * http://tools.ietf.org/html/draft-ietf-oauth-v2-26#section-4.1.3
		 * 
		 * The Authorization header contains the <client_id>:<client_secret>
		 * string, which is base64 encoded. The <client_id> is the application
		 * ID and <client_secret> is the secret which is handed out-of-band
		 */
		if (authorization == null) {
			throw new WebApplicationException(
					Response.status(Response.Status.UNAUTHORIZED)
							.entity("Base64 encoded authorization header is required when obtaining an access token")
							.type(MediaType.TEXT_PLAIN_TYPE).build());
		}
		String[] values = new String(
					Base64.decode(
						authorization.substring("Basic ".length()).getBytes()
					)
				).split(":");
		String clientId = values[0];
		String secret = values[1];
		Optional<Spiller> opt = tokenStore.getSpillerByAuthorizationCode(code);
		if (opt.isPresent()) {
			Spiller spiller = opt.get();
			
			Legitimasjon leg = new Legitimasjon();
			leg.setClientId(clientID);
			leg.setSecret(clientSecret);
			leg.setCode(code);
			leg.setRedirectUri(redirectUri);
			leg.setSpiller(spiller);
			logger.debug("Handing out access token for client {} with secret {}", clientId, secret);
			try {
				return tokenStore.storeAccessToken(leg);
			} catch (AutorisasjonsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		throw new WebApplicationException(Response
				.status(Response.Status.UNAUTHORIZED)
				.entity("Invalid authorization")
				.type(MediaType.TEXT_PLAIN_TYPE).build());
	}

	/**
	 * Request: response_type Required. Must be set to token . client_id
	 * Required. The client identifier as assigned by the authorization server,
	 * when the client was registered. redirect_uri Optional. The redirect URI
	 * registered by the client. scope Optional. The possible scope of the
	 * request. state Optional (recommended). Any client state that needs to be
	 * passed on to the client request URI.
	 * 
	 * Response access_token Required. The access token assigned by the
	 * authorization server. token_type Required. The type of the token
	 * expires_in Recommended. A number of seconds after which the access token
	 * expires. scope Optional. The scope of the access token. state Required,
	 * if present in the autorization request. Must be same value as state
	 * parameter in request.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/implicit")
	public AccessToken loginImplicit(
			@HeaderParam("Authorization") String authorization,
			@FormParam("redirect_uri") String redirectUri,
			@FormParam("client_id") String clientId,
			@FormParam("scope") String scope,
			@FormParam("reponse_type") String responseType) {
		
		if (authorization == null) {
			throw new WebApplicationException(
					Response.status(Response.Status.UNAUTHORIZED)
							.entity("Base64 encoded authorization header is required when obtaining an access token")
							.type(MediaType.TEXT_PLAIN_TYPE).build());
		}
		String[] values = new String(
				Base64.decode(
					authorization.substring("Basic ".length()).getBytes()
				)
			).split(":");
		
		BasicCredentials credentials = new BasicCredentials(values[0], values[1]);
		Optional<Spiller> spiller = lobbyService.getSpiller(credentials);
		
		if(!spiller.isPresent()){
			throw new WebApplicationException(
					Response.status(Response.Status.UNAUTHORIZED)
							.entity("Feil i brukernavn eller passord")
							.type(MediaType.TEXT_PLAIN_TYPE).build());
		}

		Legitimasjon legitimasjon = new Legitimasjon()
				.setClientId(clientId)
				.setSecret(oAuth2Verdier.getClientSecret())
				.setResponseType(responseType)
				.setRedirectUri(redirectUri)
				.setScope(scope).setState("ok");

		// String authorizationCode =
		// tokenStore.storeAuthorizationCode(legitimasjon);

		AccessToken token = null;
		try {
			token = tokenStore.storeAccessToken(legitimasjon);
		} catch (AutorisasjonsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Response result = tryRedirect(redirectUri).header("Set-Cookie",
		// token.getAccess_token()).build();

		return token;

		/*
		 * if (authorization == null) { throw new
		 * WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
		 * .entity(
		 * "Base64 encoded authorization header is required when obtaining an access token"
		 * ) .type(MediaType.TEXT_PLAIN_TYPE).build()); } String[] values = new
		 * String
		 * (Base64.decode(authorization.substring("Basic ".length()))).split
		 * (":"); String clientId = values[0]; String secret = values[1];
		 * Optional<Spiller> opt =
		 * tokenStore.getSpillerByAuthorizationCode(code); if (opt.isPresent())
		 * { Spiller clientDetails = opt.get();
		 * //LOG.debug("Handing out access token for client {} with secret {}",
		 * clientId, secret); return tokenStore.storeAccessToken(new
		 * Legitimasjon()); } throw new
		 * WebApplicationException(Response.status(Response
		 * .Status.UNAUTHORIZED).entity("Invalid authorization")
		 * .type(MediaType.TEXT_PLAIN_TYPE).build()); /* Hook for implementation
		 * of Implicit Grant flow
		 *//*
			 * boolean autorisert = autorisert(spiller, authorizationCode);
			 * 
			 * if (autorisert) { return tryRedirect(redirectUri, scope,
			 * authorizationCode); } else { return
			 * Response.status(Response.Status.UNAUTHORIZED).build(); }
			 */

		// return new AccessToken("HAHAH", Long.MAX_VALUE);
	}

}