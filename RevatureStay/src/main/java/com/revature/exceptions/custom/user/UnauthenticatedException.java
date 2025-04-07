package com.revature.exceptions.custom.user;

public class UnauthenticatedException extends RuntimeException {
    public UnauthenticatedException() {
        super("User is not authenticated");
    }
}
