package com.holtebu.bosetterne.service.auth;

import com.google.common.base.Function;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthFilter.Tuple;

import javax.annotation.Nullable;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by Espen on 25.04.2015.
 */
public class SecurityContextFunction implements Function<Tuple, SecurityContext> {
        @Nullable
        @Override
        public SecurityContext apply(final Tuple input) {

            return new SecurityContext() {

                @Override
                public Principal getUserPrincipal() {
                    return input.getPrincipal();
                }

                @Override
                public boolean isUserInRole(String role) {
                    return getUserPrincipal().getClass().getSimpleName().equals(role);
                }

                @Override
                public boolean isSecure() {
                    return input.getContainerRequestContext().getSecurityContext().isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return SecurityContext.BASIC_AUTH;
                }
            };
        }

    @Override
    public boolean equals(@Nullable Object object) {
        return false;
    }
}