package com.amalstack.notebooksfx.util.http;

import java.net.http.HttpRequest;
import java.util.concurrent.CompletableFuture;

public interface AsyncHttpClientService {

    <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(Endpoint endpoint, String method);

    <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(Endpoint endpoint, String method, T object);

    <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(Endpoint endpoint, String method, Class<T> responseClass);

    <S, T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(Endpoint endpoint, String method, S object, Class<T> responseClass);

    <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(HttpRequest request, Class<T> responseClass);

    <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(HttpRequest request);


    default <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> getAsync(Endpoint endpoint, Class<T> responseClass) {
        return sendAsync(endpoint, "GET", responseClass);
    }

    default <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> postAsync(Endpoint endpoint, T object) {
        return sendAsync(endpoint, "POST", object);
    }

    default <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> putAsync(Endpoint endpoint, T object) {
        return sendAsync(endpoint, "PUT", object);
    }

    default <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> patchAsync(Endpoint endpoint, T object) {
        return sendAsync(endpoint, "PATCH", object);
    }

    default <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> deleteAsync(Endpoint endpoint) {
        return sendAsync(endpoint, "DELETE");
    }
}
