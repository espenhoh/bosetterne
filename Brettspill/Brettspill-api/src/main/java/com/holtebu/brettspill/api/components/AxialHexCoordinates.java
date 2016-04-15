package com.holtebu.brettspill.api.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * Created by Espen on 03.04.2016.
 */
public class AxialHexCoordinates implements HexCoordinates {

    private final int q;

    private final int r;

    @JsonCreator
    public AxialHexCoordinates(@JsonProperty("q") int q, @JsonProperty("r") int r) {
        this.q = q;
        this.r = r;
    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    @JsonIgnore
    @Override
    public HexCoordinates getAxial() {
        return this;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(q, r);
    }

    @JsonIgnore
    @Override
    public HexCoordinates getCube() {
        return new CubeHexCoordinates(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AxialHexCoordinates that = (AxialHexCoordinates) o;
        return q == that.q &&
                r == that.r;
    }
}
