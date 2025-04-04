package com.revature.exceptions.custom.hotel;

public class InvalidHotelPhoneNumberException extends RuntimeException {
    public InvalidHotelPhoneNumberException(String message) {
        super(message);
    }
}
