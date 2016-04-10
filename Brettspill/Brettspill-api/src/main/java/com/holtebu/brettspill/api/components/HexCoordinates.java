package com.holtebu.brettspill.api.components;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by Espen on 03.04.2016.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = AxialHexCoordinates.class, name = "AxialHex"),
        @Type(value = CubeHexCoordinates.class, name = "CubeHex")})
public interface HexCoordinates extends Coordinates {

    /**
     * Converts these hexagonal cooridnates to axial coordiantes
     * @return Axial representation
     */
    AxialHexCoordinates getAxial();

    /**
     * Converts these hexagonal cooridnates to cube hegagonal coordiantes
     * @return Cube representation
     */
    CubeHexCoordinates getCube();
}
