package com.revature.exceptions.handlers;

import com.revature.utils.ExceptionResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {

  public ResponseEntity<Map<String, String>> handleException(Exception e) {
    return ExceptionResponseBuilder.buildResponse("An unexpected error occurred: " + e.getMessage(),
                                                  HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
