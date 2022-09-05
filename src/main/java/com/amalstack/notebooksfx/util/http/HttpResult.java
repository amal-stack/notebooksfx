package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;

import java.net.http.HttpResponse;
import java.util.Optional;

public class HttpResult<T> {
    private final T object;
    private final ErrorResponse error;
    private final HttpResponse<String> response;
    private final ResponseStatus status;

    private HttpResult(HttpResponse<String> response, T object, ErrorResponse error) {
        this.object = object;
        this.error = error;
        this.response = response;
        this.status = ResponseStatus.of(response.statusCode());
    }


    public Optional<T> getObject() {
        return Optional.ofNullable(object);
    }

    public Optional<ErrorResponse> getError() {
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

    public static <T> Builder<T> builder(Class<T> type) {
        return new Builder<>();
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private HttpResponse<String> response;
        private T object;
        private ErrorResponse error;
        private boolean createDefaultError;

        private Builder() {
        }

        public Builder<T> withResponse(HttpResponse<String> response) {
            this.response = response;
            return this;
        }

        public Builder<T> withObject(T object) {
            this.object = object;
            return this;
        }

        public Builder<T> withError(ErrorResponse error) {
            this.error = error;
            return this;
        }

        public Builder<T> withDefaultError() {
            createDefaultError = true;
            return this;
        }

        public HttpResult<T> build() {
            if (error == null && createDefaultError) {
                error = new ErrorResponse(
                        response.statusCode(),
                        ResponseStatus.of(response.statusCode()).name());
            }
            return new HttpResult<>(response, object, error);
        }
    }
}
