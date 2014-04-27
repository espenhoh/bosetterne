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

	private Polettlager<AccessToken, Spiller ,Legitimasjon, String> tokenStore;
	private LobbyService<Spiller, BasicCredentials> lobbyService;

	public OAuthAuthorizeResource(
			Polettlager<AccessToken, Spiller ,Legitimasjon, String> tokenStore,
			LobbyService<Spiller, BasicCredentials> lobbyService) {
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
	public Response login(@FormParam("username") String brukernavn,
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
		Legitimasjon legitimasjon = new Legitimasjon();
		BasicCredentials credentials = new BasicCredentials(brukernavn, passord);
		Spiller spiller = lobbyService.getSpiller(credentials);
		//String authorizationCode = tokenStore.storeAuthorizationCode(spiller); // .setResponseType("code").setClientId(clientId).setRedirectUri(redirectUri).setScope("read").setState(state)
		String authorizationCode = "temp";
		/*
		 * Hook for implementation of Implicit Grant flow
		 */
		if (spiller != null && authorizationCode != null) {
			String uri = String.format(redirectUri.concat("?")
					.concat("code=%s").concat("&state=%s"), "", // authorizationCode,
					state);
			try {
				return Response.seeOther(new URI(uri)).build();
			} catch (URISyntaxException e) {
				throw new RuntimeException(String.format(
						"Redirect URI '%s' is not valid", uri));
			}
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
