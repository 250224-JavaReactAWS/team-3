package com.revature.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ExceptionResponseBuilder {
  public static ResponseEntity<Map<String, String>> buildResponse(String message,
                                                                  HttpStatus status){
    Map<String, String> response = new HashMap<>();
    response.put("error", message);
    return ResponseEntity.status(status).body(response);
  }
}
