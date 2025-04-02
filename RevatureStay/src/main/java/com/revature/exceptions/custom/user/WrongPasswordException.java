package com.revature.exceptions.custom.user;

public class WrongPasswordException extends RuntimeException {
  public WrongPasswordException(String message) {
    super(message);
  }
}
