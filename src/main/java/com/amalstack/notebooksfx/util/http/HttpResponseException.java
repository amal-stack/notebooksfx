package com.amalstack.notebooksfx.util.http;

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
        super(createMessage(errorResponse));
        this.errorResponse = errorResponse;
    }

    public HttpResponseException(ErrorResponse errorResponse, String message) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public HttpResponseException(ErrorResponse errorResponse, Throwable cause) {
        super(createMessage(errorResponse), cause);
        this.errorResponse = errorResponse;
    }

    public HttpResponseException(ErrorResponse errorResponse, String message, Throwable cause) {
        super(message, cause);
        this.errorResponse = errorResponse;
    }

    private static String createMessage(ErrorResponse errorResponse) {
        return String.format("%d: %s",
                errorResponse.status(),
                errorResponse.message()
                        .orElse(errorResponse.error()));
    }

    public Optional<ErrorResponse> getErrorResponse() {
        return Optional.ofNullable(errorResponse);
    }

    public int getStatusCode() {
        return errorResponse.status();
    }
}
