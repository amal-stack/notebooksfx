package com.amalstack.notebooksfx.util.http;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class DefaultErrorResponse implements ErrorResponse {

    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final String timestamp;
    private final Map<String, String> errors;

    @JsonCreator
    public DefaultErrorResponse(
            @JsonProperty("status") int status,
            @JsonProperty("error") String error,
            @JsonProperty("message") String message,
            @JsonProperty("path") String path,
            @JsonProperty("timestamp") String timestamp,
            @JsonProperty("errors") Map<String, String> errors) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
        this.errors = errors == null
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(errors);
    }

    public static DefaultErrorResponse fromResponse(HttpResponse<?> response) {
        return new DefaultErrorResponse(response.statusCode(),
                ResponseStatus.of(response.statusCode()).label(),
                null,
                response.uri().getPath(),
                null, Collections.emptyMap());
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public String error() {
        return error == null ? ErrorResponse.super.error() : error;
    }

    @Override
    public Optional<String> message() {
        return Optional.ofNullable(message);
    }

    @Override
    public Optional<String> path() {
        return Optional.ofNullable(path);
    }

    @Override
    public Optional<String> timestamp() {
        return Optional.ofNullable(timestamp);
    }

    @Override
    public Map<String, String> errors() {
        return errors;
    }

    @Override
    public String toString() {
        return "DefaultErrorResponse[" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", errors=" + errors +
                ']';
    }
}
