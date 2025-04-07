package com.revature.exceptions.custom.hotel;

public class InvalidHotelNameException extends RuntimeException {
    public InvalidHotelNameException(String message) {
        super(message);
    }
}
