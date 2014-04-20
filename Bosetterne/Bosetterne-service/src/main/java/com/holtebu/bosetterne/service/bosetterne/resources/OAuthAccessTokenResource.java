package com.holtebu.bosetterne.service.bosetterne.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.holtebu.bosetterne.api.Spiller;
import com.holtebu.bosetterne.service.bosetterne.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.bosetterne.core.AccessToken;
import com.google.common.base.Optional;
import com.sun.jersey.core.util.Base64;
//import com.yammer.dropwizard.logging.Log;

/**
* Entry point for oauth calls
*
*/
@Path("/token")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class OAuthAccessTokenResource {

  private final Polettlager<AccessToken, Spiller, String> tokenStore;

  //private static final Log LOG = Log.forClass(OAuthAccessTokenResource.class);

  public OAuthAccessTokenResource(Polettlager<AccessToken, Spiller, String> tokenStore) {
    super();
    this.tokenStore = tokenStore;
  }

  @POST
  public AccessToken accessToken(
		  @HeaderParam("Authorization") String authorization,
		  @FormParam("grant_type") String grantType,
		  @FormParam("code") String code,
		  @FormParam("redirect_uri") String redirectUri) {
    /*
* http://tools.ietf.org/html/draft-ietf-oauth-v2-26#section-4.1.3
*
* The Authorization header contains the <client_id>:<client_secret> string,
* which is base64 encoded. The <client_id> is the application ID and
* <client_secret> is the secret which is handed out-of-band
*/
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
      	return tokenStore.storeAccessToken(clientDetails);
    }
    throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid authorization")
        .type(MediaType.TEXT_PLAIN_TYPE).build());
  }

}