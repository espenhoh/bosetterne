package com.holtebu.bosetterne.service.auth;

import com.holtebu.bosetterne.api.lobby.Spiller;
import com.holtebu.bosetterne.api.lobby.SpillerBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Espen on 20.09.2015.
 */
public class BosetterneAuthorizerTest {

    private BosetterneAuthorizer authorizer;

    @Before
    public void setUp() throws Exception {
        authorizer = new BosetterneAuthorizer();

    }

    @Test
    public void testAuthorize() throws Exception {
        Spiller spiller = SpillerBuilder.lagTestspiller();
        String role ="";

        boolean authorized = authorizer.authorize(spiller, role);
        assertThat("Tilgang", authorized, equalTo(true));

    }


}