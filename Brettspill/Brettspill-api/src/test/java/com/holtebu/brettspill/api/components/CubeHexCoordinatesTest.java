package com.holtebu.brettspill.api.components;


import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import static io.dropwizard.testing.FixtureHelpers.*;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Created by Espen on 03.04.2016.
 */
public class CubeHexCoordinatesTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private HexCoordinates cubeHexCoordinates;

    private CubeHexCoordinates readFixture;

    @Before
    public void setUp() throws Exception {
        cubeHexCoordinates = new CubeHexCoordinates(0, 0, 0);
        readFixture = MAPPER.readValue(fixture("fixtures/cubeHex.json"), CubeHexCoordinates.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void producesCubeFromAxial() throws Exception {


        CubeHexCoordinates convertedCoordinates = new CubeHexCoordinates(new AxialHexCoordinates(0,0));

        assertThat("X coorindates should be the same",convertedCoordinates,is(equalTo(cubeHexCoordinates)));


    }

    @Test
    public void getAxial() throws Exception {
        AxialHexCoordinates axialHexCoordinates = cubeHexCoordinates.getAxial();
        AxialHexCoordinates expected = new AxialHexCoordinates(0,0);

        assertThat("Axial coordinates must be equal to converted cube coordinates",axialHexCoordinates,is(equalTo(expected)));


    }

    @Test
    public void serializesToJSON() throws Exception {
        String written = MAPPER.writeValueAsString(cubeHexCoordinates);
        String  expectedString = MAPPER.writeValueAsString(readFixture);
        assertThat("Should be possible to make JSON out of Cube Coordinates",written,is(equalTo(expectedString)));
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat("Should be possible to make Cube Coordinates out of JSON",readFixture,is(equalTo(cubeHexCoordinates)));
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(CubeHexCoordinates.class)
                .usingGetClass()
                .verify();
    }

//0

}