package com.amalstack.notebooksfx.util.http;

public class EndpointException extends RuntimeException {
    public EndpointException(String message) {
        super(message);
    }

    public EndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndpointException(Throwable cause) {
        super(cause);
    }

}

