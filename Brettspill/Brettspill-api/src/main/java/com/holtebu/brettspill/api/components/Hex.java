package com.holtebu.brettspill.api.components;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Espen on 31.03.2016.
 */
public class Hex implements HexBoardPiece {

    @JsonProperty("coordinates")
    public CubeHexCoordinates getCoordinates() {
        return coordinates;
    }


    private final CubeHexCoordinates coordinates;


    public Hex(CubeHexCoordinates coordinates) {
        this.coordinates = coordinates;
    }




}
