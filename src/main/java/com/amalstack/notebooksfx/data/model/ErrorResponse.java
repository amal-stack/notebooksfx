package com.amalstack.notebooksfx.data.model;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        String timestamp,
        FieldError... fieldErrors) {

    public record FieldError(String field, String message) {
    }

    public ErrorResponse(int status, String message) {
        this(status, null, message, null, null);
    }
}



