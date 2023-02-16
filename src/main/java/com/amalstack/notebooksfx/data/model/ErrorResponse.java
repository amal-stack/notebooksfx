package com.amalstack.notebooksfx.data.model;

import com.amalstack.notebooksfx.util.http.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final String timestamp;
    private final Map<String, String> errors;

    @JsonCreator
    public ErrorResponse(
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
        this.errors = errors == null ? null : Collections.unmodifiableMap(errors);
    }


    public static ErrorResponse fromResponse(HttpResponse<?> response) {
        return new ErrorResponse(response.statusCode(),
                ResponseStatus.of(response.statusCode()).name(),
                null,
                response.uri().getPath(),
                null, new HashMap<>());
    }

    public int status() {
        return status;
    }

    public String error() {
        return error;
    }

    public String message() {
        return message;
    }

    public String path() {
        return path;
    }

    public String timestamp() {
        return timestamp;
    }

    public Map<String, String> errors() {
        return errors;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ErrorResponse) obj;
        return this.status == that.status &&
                Objects.equals(this.error, that.error) &&
                Objects.equals(this.message, that.message) &&
                Objects.equals(this.path, that.path) &&
                Objects.equals(this.timestamp, that.timestamp) &&
                Objects.equals(this.errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, error, message, path, timestamp, errors);
    }

    @Override
    public String toString() {
        return "ErrorResponse[" +
                "status=" + status + ", " +
                "error=" + error + ", " +
                "message=" + message + ", " +
                "path=" + path + ", " +
                "timestamp=" + timestamp + ", " +
                "errors=" + errors + ']';
    }

}