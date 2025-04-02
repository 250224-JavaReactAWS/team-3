package com.revature.exceptions;

public class WrongEmailException extends RuntimeException {
  public WrongEmailException(String message) {
    super(message);
  }
}
