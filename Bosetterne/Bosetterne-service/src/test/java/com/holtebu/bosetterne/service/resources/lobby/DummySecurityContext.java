package com.holtebu.bosetterne.service.resources.lobby;

import com.holtebu.bosetterne.api.lobby.Spiller;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by Espen on 26.04.2015.
 */
public class DummySecurityContext implements SecurityContext {

    private final Spiller testSpiller;

    public DummySecurityContext(Spiller testSpiller) {
        this.testSpiller = testSpiller;
    }

    @Override
    public Principal getUserPrincipal() {
        return testSpiller;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
