package com.revature.exceptions.custom.hotel;

public class InvalidHotelDescriptionException extends RuntimeException {
    public InvalidHotelDescriptionException(String message) {
        super(message);
    }
}
