package com.revature.exceptions.custom.user;

public class EmailAlreadyRegisteredException extends RuntimeException {
  public EmailAlreadyRegisteredException(String message) {
    super(message);
  }
}
