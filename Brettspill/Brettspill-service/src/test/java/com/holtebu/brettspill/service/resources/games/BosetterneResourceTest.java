package com.holtebu.brettspill.service.resources.games;

import com.holtebu.brettspill.service.ConfigurationStub;
import org.glassfish.hk2.api.ServiceLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Espen on 15.04.2017.
 */
public class BosetterneResourceTest {

    private BosetterneResource res;

    @Before
    public void setUp() throws Exception {
        ServiceLocator locator = ConfigurationStub.getLocator();

        res = locator.getService(BosetterneResource.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sayHex() throws Exception {
    }

    @Test
    public void getBosetterneBoard() throws Exception {
    }

    @Test
    public void getIt() throws Exception {
    }

    @Test
    public void putIt() throws Exception {
    }

    @Test
    public void postMsg() throws Exception {
    }

}