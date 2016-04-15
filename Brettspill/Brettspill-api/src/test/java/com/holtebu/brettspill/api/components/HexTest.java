package com.holtebu.brettspill.api.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Espen on 31.03.2016.
 */
public class HexTest {

    private Hex hex;
    private HexBoardPiece readFixture;

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Before
    public void setUp() throws Exception {
        HexCoordinates hexCoordinates = new CubeHexCoordinates(1,0,-1);
        readFixture = MAPPER.readValue(fixture("fixtures/hex.json"), HexBoardPiece.class);
        hex = new Hex(hexCoordinates);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void serializesToJSON() throws Exception {
        String written = MAPPER.writeValueAsString(hex);
        String  expectedString = MAPPER.writeValueAsString(readFixture);
        assertThat("Should be possible to make JSON out of a Hex",written,is(equalTo(expectedString)));
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat("Should be possible to make Hex out of JSON",readFixture,is(equalTo(hex)));
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Hex.class)
                .usingGetClass()
                .verify();
    }
}