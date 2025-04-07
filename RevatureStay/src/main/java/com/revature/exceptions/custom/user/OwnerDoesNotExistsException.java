package com.revature.exceptions.custom.user;

public class OwnerDoesNotExistsException extends RuntimeException {
    public OwnerDoesNotExistsException(String message) {
        super(message);
    }
}
