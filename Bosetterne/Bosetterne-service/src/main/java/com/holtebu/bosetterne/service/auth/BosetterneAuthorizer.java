package com.holtebu.bosetterne.service.auth;

import com.holtebu.bosetterne.api.lobby.Spiller;
import io.dropwizard.auth.Authorizer;

/**
 * Created by Espen on 20.09.2015.
 */
public class BosetterneAuthorizer implements Authorizer<Spiller>{
    @Override
    public boolean authorize(Spiller principal, String role) {
        return true;
    }
}
