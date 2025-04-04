package com.revature.exceptions.handlers;

import com.revature.exceptions.custom.user.*;
import com.revature.utils.ExceptionResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler {
  @ExceptionHandler(EmailAlreadyRegisteredException.class)
  public ResponseEntity<Map<String, String>> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException e) {
    return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(InvalidEmailFormatException.class)
  public ResponseEntity<Map<String, String>> handleInvalidEmailFormatException(InvalidEmailFormatException e) {
    return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<Map<String, String>> handleInvalidPasswordException(InvalidPasswordException e) {
    return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(WrongEmailException.class)
  public ResponseEntity<Map<String, String>> handleWrongEmailException(WrongEmailException e) {
    return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(WrongPasswordException.class)
  public ResponseEntity<Map<String, String>> handleWrongPasswordException(WrongPasswordException e) {
    return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
  }
}
