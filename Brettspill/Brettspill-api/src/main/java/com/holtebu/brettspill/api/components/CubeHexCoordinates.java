package com.holtebu.brettspill.api.components;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * Created by Espen on 03.04.2016.
 */
public class CubeHexCoordinates implements HexCoordinates {

    @JsonProperty("x")
    private final int x;

    @JsonProperty("y")
    private final int y;

    @JsonProperty("z")
    private final int z;

    @JsonCreator
    public CubeHexCoordinates(@JsonProperty("x") int x, @JsonProperty("y") int y, @JsonProperty("z") int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @JsonIgnore
    CubeHexCoordinates(AxialHexCoordinates axialHexCoordinates) {
        this.x = axialHexCoordinates.getQ();
        this.y = axialHexCoordinates.getR();
        this.z = 0 - x - y;
    }

    @Override
    @JsonIgnore
    public AxialHexCoordinates getAxial() {
        return new AxialHexCoordinates(x,y);
    }

    @Override
    @JsonIgnore
    public CubeHexCoordinates getCube() {
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CubeHexCoordinates that = (CubeHexCoordinates) o;
        return x == that.x &&
                y == that.y &&
                z == that.z;
    }


}
