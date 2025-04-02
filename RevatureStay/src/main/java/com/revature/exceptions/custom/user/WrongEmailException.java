package com.revature.exceptions.custom.user;

public class WrongEmailException extends RuntimeException {
  public WrongEmailException(String message) {
    super(message);
  }
}
