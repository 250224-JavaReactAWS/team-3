package com.revature.exceptions.custom.user;

public class InvalidEmailFormatException extends RuntimeException {
  public InvalidEmailFormatException(String message) {
    super(message);
  }
}
