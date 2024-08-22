package com.example.ecom_app.exceptions;

public class APIException extends RuntimeException {
    String message;

    public APIException(String message) {
        super(message);
        this.message = message;
    }

    public APIException() {
    }
}
