package com.revature.exceptions.handlers;

import com.revature.exceptions.custom.hotel.*;
import com.revature.utils.ExceptionResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class HotelExceptionHandler {

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleHotelNotFoundException (HotelNotFoundException e){
        return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidHotelAddressException.class)
    public ResponseEntity<Map<String,String>> handleInvalidHotelAddressException (InvalidHotelAddressException e){
        return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidHotelDescriptionException.class)
    public ResponseEntity<Map<String,String>> handleInvalidHotelDescriptionException (InvalidHotelDescriptionException e){
        return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidHotelNameException.class)
    public ResponseEntity<Map<String,String>> handleInvalidHotelNameException (InvalidHotelNameException e){
        return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidHotelPhoneNumberException.class)
    public ResponseEntity<Map<String,String>> handleInvalidHotelPhoneNumberException (InvalidHotelPhoneNumberException e){
        return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
