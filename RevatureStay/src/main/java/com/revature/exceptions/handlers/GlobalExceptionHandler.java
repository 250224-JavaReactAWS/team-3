package com.revature.exceptions.handlers;

import com.revature.utils.ExceptionResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
  private final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);

  public ResponseEntity<Map<String, String>> handleException(Exception e) {
    logger.error(e.getMessage());
    return ExceptionResponseBuilder.buildResponse("An unexpected error occurred: " + e.getMessage(),
                                                  HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgumentException (IllegalArgumentException e){
    return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
