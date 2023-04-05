package com.amalstack.notebooksfx.util.http;

import java.util.Map;
import java.util.Optional;

public interface ErrorResponse {
    int status();

    default String error() {
        return ResponseStatus.of(status()).name();
    }

    Optional<String> message();

    Optional<String> path();

    Optional<String> timestamp();

    Map<String, String> errors();
}

