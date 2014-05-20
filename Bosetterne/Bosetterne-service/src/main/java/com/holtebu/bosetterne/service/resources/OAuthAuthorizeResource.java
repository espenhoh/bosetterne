package com.holtebu.bosetterne.service.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
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
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import com.sun.jersey.core.util.Base64;

import io.dropwizard.auth.basic.BasicCredentials;

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
			redirectUri = authorizationCodeRedirectURI(redirectUri, state, authorizationCode);
			return tryRedirect(redirectUri).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	/**
	 * Request:
	 * response_type 	Required. Must be set to token .
	 * client_id 	Required. The client identifier as assigned by the authorization server, when the client was registered.
	 * redirect_uri 	Optional. The redirect URI registered by the client.
	 * scope 	Optional. The possible scope of the request.
	 * state 	Optional (recommended). Any client state that needs to be passed on to the client request URI.
	 * 
	 * Response
	 * 	access_token 	Required. The access token assigned by the authorization server.
	 * 	token_type 	Required. The type of the token
	 * 	expires_in 	Recommended. A number of seconds after which the access token expires.
	 * 	scope 	Optional. The scope of the access token.
	 * 	state 	Required, if present in the autorization request. Must be same value as state parameter in request.*/
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/implicit")
	public AccessToken loginImplicit(
			@FormParam("username") String brukernavn,
			@FormParam("password") String passord,
			@FormParam("redirect_uri") String redirectUri,
			@FormParam("client_id") String clientId,
			@FormParam("scope") String scope, 
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
				setSecret(oAuth2Verdier.getClientSecret()).
				setResponseType("code").
				setRedirectUri(redirectUri).
				setScope("bosetterne").
				setState(scope);
		
		//String authorizationCode = tokenStore.storeAuthorizationCode(legitimasjon);

		AccessToken token = tokenStore.storeAccessToken(legitimasjon);
		
		
		
		Response result = tryRedirect(redirectUri).header("Set-Cookie", token.getAccess_token()).build();
		
		return token;
		
		/*
		if (authorization == null) {
		      throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
		          .entity("Base64 encoded authorization header is required when obtaining an access token")
		          .type(MediaType.TEXT_PLAIN_TYPE).build());
		    }
		    String[] values = new String(Base64.decode(authorization.substring("Basic ".length()))).split(":");
		    String clientId = values[0];
		    String secret = values[1];
		    Optional<Spiller> opt = tokenStore.getSpillerByAuthorizationCode(code);
		    if (opt.isPresent()) {
		    	Spiller clientDetails = opt.get();
		    	//LOG.debug("Handing out access token for client {} with secret {}", clientId, secret);
		      	return tokenStore.storeAccessToken(new Legitimasjon());
		    }
		    throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid authorization")
		        .type(MediaType.TEXT_PLAIN_TYPE).build());
		/*
		 * Hook for implementation of Implicit Grant flow
		 *//*
		boolean autorisert = autorisert(spiller, authorizationCode);
		
		if (autorisert) {
			return tryRedirect(redirectUri, scope, authorizationCode);
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}*/
		
		//return new AccessToken("HAHAH", Long.MAX_VALUE);
	}
	
	String authorizationCodeRedirectURI(String redirectUri, String state, String authorizationCode) {
		String format = redirectUri.concat("?").concat("code=%s").concat("&state=%s");
		return String.format(format, authorizationCode, state);
	}
	
	private String accessTokenRedirectURI(String redirectUri, String state, String authorizationCode) {
		String format = redirectUri.concat("?").concat("code=%s").concat("&state=%s");
		return String.format(format, authorizationCode, state);
	}

	private ResponseBuilder tryRedirect(String uri) {
		try {
			return Response.seeOther(new URI(uri));
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
