package com.holtebu.brettspill.api.exceptions;

import javax.ws.rs.WebApplicationException;

/**
 * Thrown when trying to make a coordinate which does not conform to the coordinate rules
 * @author Espen Holtebu
 * @since 2016-04-10
 */
public class CoordinateException extends Exception {
    public CoordinateException(String message) {
        super(message);
    }
}
