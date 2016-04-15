package com.holtebu.brettspill.api.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * Created by Espen on 31.03.2016.
 */
public class Hex implements HexBoardPiece {

    @JsonProperty()
    public HexCoordinates getCoordinates() {
        return coordinates.getCube();
    }


    private final HexCoordinates coordinates;


    @JsonCreator
    public Hex(@JsonProperty("coordinates") HexCoordinates coordinates) {
        this.coordinates = coordinates.getAxial();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hex hex = (Hex) o;
        return Objects.equal(coordinates, hex.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinates);
    }
}
