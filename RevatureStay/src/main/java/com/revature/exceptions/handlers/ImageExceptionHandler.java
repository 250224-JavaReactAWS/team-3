package com.revature.exceptions.handlers;

import com.revature.exceptions.custom.image.ImageNotFoundException;
import com.revature.utils.ExceptionResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ImageExceptionHandler {

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleImageNotFoundException (ImageNotFoundException e){
        return ExceptionResponseBuilder.buildResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
