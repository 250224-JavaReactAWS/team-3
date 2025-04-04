package com.revature.exceptions.custom.hotel;

public class InvalidHotelDescription extends RuntimeException {
    public InvalidHotelDescription(String message) {
        super(message);
    }
}
