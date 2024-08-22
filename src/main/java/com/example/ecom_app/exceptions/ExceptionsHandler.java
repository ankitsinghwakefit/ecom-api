package com.example.ecom_app.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // specialized version of controller advice
public class ExceptionsHandler extends RuntimeException {

    // also add Exception.class to handle all exceptions that are thrown by our app

    @ExceptionHandler(MethodArgumentNotValidException.class) // this method will handle MethodArgumentNotValidException
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            response.put(fieldName, errorMessage);
        });
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MyNotFoundException.class)
    public ResponseEntity<String> myResourceNotFoundException(MyNotFoundException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> myAPIException(APIException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }
}
