package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;

import java.net.http.HttpResponse;
import java.util.Optional;

public class HttpResult<T, E extends ErrorResponse> {
    private final T object;
    private final E error;
    private final HttpResponse<String> response;
    private final ResponseStatus status;

    private HttpResult(HttpResponse<String> response, T object, E error) {
        this.object = object;
        this.error = error;
        this.response = response;
        this.status = ResponseStatus.of(response.statusCode());
    }


    public Optional<T> getObject() {
        return Optional.ofNullable(object);
    }

    public Optional<E> getError() {
        return Optional.ofNullable(error);
    }

    public HttpResponse<String> getResponse() {
        return response;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return status == ResponseStatus.SUCCESS;
    }

    public static <T> HttpResult<T, ? extends ErrorResponse> empty(HttpResponse<String> response) {
        return new HttpResult<>(response, null, null);
    }

    public static <T> HttpResult<T, ? extends ErrorResponse> ofObject(HttpResponse<String> response, T object) {
        return new HttpResult<>(response, object, null);
    }

    public static <T> HttpResult<T, ? extends ErrorResponse> ofError(HttpResponse<String> response, ErrorResponse error) {
        return new HttpResult<>(response, null, error);
    }
}