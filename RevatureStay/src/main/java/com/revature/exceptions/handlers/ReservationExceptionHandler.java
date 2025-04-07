package com.revature.exceptions.handlers;

import com.revature.exceptions.custom.reservation.InternalException;
import com.revature.exceptions.custom.reservation.InvalidDatesException;
import com.revature.exceptions.custom.reservation.ResourceNotFoundException;
import com.revature.exceptions.custom.reservation.RoomNotAvailableException;
import com.revature.utils.ExceptionResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<Map<String, String>> handleInternalException(InternalException e){
        return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDatesException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDatesException(InvalidDatesException e){
        return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException e){
        return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<Map<String, String>> handleRoomNotAvailableException(RoomNotAvailableException e){
        return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.CONFLICT);
    }
}
