package com.holtebu.brettspill.api.components.boardcomponents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holtebu.brettspill.api.components.boardcomponents.AxialHexCoordinates;
import com.holtebu.brettspill.api.components.boardcomponents.CubeHexCoordinates;
import com.holtebu.brettspill.api.components.boardcomponents.HexCoordinates;
import io.dropwizard.jackson.Jackson;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

import static io.dropwizard.testing.FixtureHelpers.fixture;
//import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Espen on 03.04.2016.
 */
public class AxialHexCoordinatesTest {
    private AxialHexCoordinates axialHexCoordinates;
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();



    private AxialHexCoordinates readFixture;

    @Before
    public void setUp() throws Exception {
        axialHexCoordinates = new AxialHexCoordinates(1,2);
        readFixture = MAPPER.readValue(fixture("fixtures/axialHex.json"), AxialHexCoordinates.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getAxial() throws Exception {
        HexCoordinates coordinates = axialHexCoordinates.getAxial();

        assertThat("Coordinates should be the same object", coordinates ,sameInstance(axialHexCoordinates));
    }

    @Test
    public void serializesToJSON() throws Exception {
        String written = MAPPER.writeValueAsString(axialHexCoordinates);
        String  expectedString = MAPPER.writeValueAsString(readFixture);
        assertThat("Should be possible to make JSON out of Axial Coordinates",written,is(equalTo(expectedString)));
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat("Should be possible to make Axial Coordinates out of JSON",readFixture,is(equalTo(axialHexCoordinates)));
    }

    @Test
    public void getCube() throws Exception {
        CubeHexCoordinates expected = new CubeHexCoordinates(1,2,-3);

        CubeHexCoordinates got = (CubeHexCoordinates) axialHexCoordinates.getCube();

        assertThat("Converted axialHexCoordinates must be equal to the original hex coordinate", got, equalTo(expected));

    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(AxialHexCoordinates.class)
                .usingGetClass()
                .verify();
    }

}