package com.revature.exceptions.custom.reservation;

public class InvalidDatesException extends RuntimeException{
    public InvalidDatesException(String message){
        super(message);
    }
}
