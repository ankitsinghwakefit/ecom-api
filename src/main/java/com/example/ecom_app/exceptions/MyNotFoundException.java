package com.example.ecom_app.exceptions;

public class MyNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public MyNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s : %s", resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public MyNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s : %d", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

    public MyNotFoundException() {
    }

}
