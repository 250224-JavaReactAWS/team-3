package com.revature.exceptions.custom.room;

public class RoomNotFoundException extends RuntimeException {
  public RoomNotFoundException(String message) {
    super(message);
  }
}
