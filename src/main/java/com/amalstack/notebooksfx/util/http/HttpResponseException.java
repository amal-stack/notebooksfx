package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;

import java.util.Optional;

public class HttpResponseException extends RuntimeException {
    private ErrorResponse errorResponse;

    public HttpResponseException(String message) {
        super(message);
    }

    public HttpResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpResponseException(ErrorResponse errorResponse) {
        super(errorResponse.message());
        this.errorResponse = errorResponse;
    }

    public HttpResponseException(ErrorResponse errorResponse, String message) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public HttpResponseException(ErrorResponse errorResponse, String message, Throwable cause) {
        super(message, cause);
        this.errorResponse = errorResponse;
    }

    public Optional<ErrorResponse> getErrorResponse() {
        return Optional.ofNullable(errorResponse);
    }
}
