package com.holtebu.brettspill.service.auth;

import com.holtebu.brettspill.api.lobby.Spiller;
import com.holtebu.brettspill.service.auth.sesjon.Polettlager;
import com.holtebu.brettspill.service.core.AccessToken;
import com.holtebu.brettspill.service.core.Legitimasjon;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.auth.Authenticator;


/**
 * Created by Espen on 26.04.2015.
 */
public class BosetterneOAuthFilter {

    private Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore;

    public BosetterneOAuthFilter(Polettlager<AccessToken, Spiller, Legitimasjon, String> tokenStore) {
        this.tokenStore = tokenStore;
    }

//    public AuthDynamicFeature getFilter(){
//        OAuthCredentialAuthFilter.Builder<Spiller,BoardgameAuthenticator> oauthBuilder = new OAuthCredentialAuthFilter.Builder<>();
//        oauthBuilder.setAuthenticator(new BoardgameAuthenticator(tokenStore))
//                .setRealm("protected-resources")
//                .setPrefix("Bearer");
//
//        return new AuthDynamicFeature(oauthBuilder.buildAuthFilter());
//    }

}
