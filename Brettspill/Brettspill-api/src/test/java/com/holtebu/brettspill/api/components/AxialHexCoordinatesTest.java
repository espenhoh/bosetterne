package com.holtebu.brettspill.api.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import static io.dropwizard.testing.FixtureHelpers.*;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import static io.dropwizard.testing.FixtureHelpers.fixture;
//import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Espen on 03.04.2016.
 */
public class AxialHexCoordinatesTest {
    private HexCoordinates axialHexCoordinates;
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();



    private AxialHexCoordinates readFixture;

    @Before
    public void setUp() throws Exception {
        axialHexCoordinates = new AxialHexCoordinates(0,0);
        readFixture = MAPPER.readValue(fixture("fixtures/axialHex.json"), AxialHexCoordinates.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getAxial() throws Exception {
        AxialHexCoordinates coordinates = axialHexCoordinates.getAxial();

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

    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(AxialHexCoordinates.class)
                .usingGetClass()
                .verify();
    }

}