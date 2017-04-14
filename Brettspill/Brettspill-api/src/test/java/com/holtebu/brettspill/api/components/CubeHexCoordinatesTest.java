package com.holtebu.brettspill.api.components;


import com.holtebu.brettspill.api.components.boardcomponents.AxialHexCoordinates;
import com.holtebu.brettspill.api.components.boardcomponents.CubeHexCoordinates;
import com.holtebu.brettspill.api.components.boardcomponents.HexCoordinates;
import com.holtebu.brettspill.api.exceptions.CoordinateException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.dropwizard.testing.FixtureHelpers.*;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.rules.ExpectedException;


/**
 * Created by Espen on 03.04.2016.
 */
public class CubeHexCoordinatesTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private HexCoordinates cubeHexCoordinates;

    private CubeHexCoordinates readFixture;

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
    public void throwsCoordinateExceptionWhenIllegalCoordinates() throws Exception {
        exception.expect(CoordinateException.class);
        exception.expectMessage(containsString("does not sum to zero"));
        CubeHexCoordinates convertedCoordinates = new CubeHexCoordinates(1,2,3);
    }

    @Test
    public void sumOfConvertetAxialCoordinatesIsZero(){
        AxialHexCoordinates axial = new AxialHexCoordinates(33,-54);
        CubeHexCoordinates convertedCoordinates = new CubeHexCoordinates(axial);

        int sum = convertedCoordinates.getX() + convertedCoordinates.getY() + convertedCoordinates.getZ();
        assertThat("Sum must be 0", sum, is(0));
    }

    @Test
    public void getAxial() throws Exception {
        AxialHexCoordinates axialHexCoordinates = (AxialHexCoordinates) cubeHexCoordinates.getAxial();
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