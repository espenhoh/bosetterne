package com.holtebu.bosetterne.service.auth;

import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.service.auth.sesjon.Polettlager;
import com.holtebu.bosetterne.service.core.AccessToken;
import com.holtebu.bosetterne.service.core.Legitimasjon;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;

/**
 * Created by Espen on 26.04.2015.
 */
public class BosetterneOAuthFilter {
    public OAuthCredentialAuthFilter<Spiller> getFilter(Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore){
        OAuthCredentialAuthFilter.Builder<Spiller,BosetterneAuthenticator> oauthBuilder = new OAuthCredentialAuthFilter.Builder<>();
        oauthBuilder.setAuthenticator(new BosetterneAuthenticator(tokenStore))
                .setRealm("protected-resources")
                .setPrefix("Bearer")
                .setSecurityContextFunction(new SecurityContextFunction());

        return oauthBuilder.buildAuthHandler();
    }

}
