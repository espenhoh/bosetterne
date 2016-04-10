package com.holtebu.brettspill.service.resources.lobby;

import com.holtebu.brettspill.api.lobby.Spiller;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by Espen on 26.04.2015.
 */
public class DummySecurityContext implements SecurityContext {

    private final Spiller testSpiller;
    private final String role;

    public DummySecurityContext(Spiller testSpiller) {
        this(testSpiller,null);
    }

    public DummySecurityContext(Spiller testSpiller, String role) {
        this.testSpiller = testSpiller;
        this.role = role;
    }

    @Override
    public Principal getUserPrincipal() {
        return testSpiller;
    }

    @Override
    public boolean isUserInRole(String role) {
        return (this.role == null ? role == null: this.role.equals(role));
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
