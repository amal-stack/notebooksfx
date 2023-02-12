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

    public static <T, E extends ErrorResponse> Builder<T, E> builder(Class<T> type, Class<E> errorType) {
        return new Builder<>();
    }

    public static <T, E extends ErrorResponse> Builder<T, E> builder() {
        return new Builder<>();
    }

    public static class Builder<T, E extends ErrorResponse> {
        private HttpResponse<String> response;
        private T object;
        private E error;

        private Builder() {
        }

        public Builder<T, E> withResponse(HttpResponse<String> response) {
            this.response = response;
            return this;
        }

        public Builder<T, E> withObject(T object) {
            this.object = object;
            return this;
        }

        public Builder<T, E> withError(E error) {
            this.error = error;
            return this;
        }

        public HttpResult<T, E> build() {
            return new HttpResult<>(response, object, error);
        }
    }
}