package com.revature.exceptions.custom.reservation;

public class ForbbidenOperationException extends RuntimeException {
    public ForbbidenOperationException(String message) {
        super(message);
    }
}
