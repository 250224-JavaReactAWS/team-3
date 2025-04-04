package com.revature.exceptions.custom.hotel;

public class InvalidHotelAddressException extends RuntimeException {
    public InvalidHotelAddressException(String message) {
        super(message);
    }
}
