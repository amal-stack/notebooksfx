package com.amalstack.notebooksfx.util.http;

import java.net.http.HttpRequest;

public interface HttpClientService {

    <T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method);

    <T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method, T object);

    <T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method, Class<T> responseClass);

    <S, T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method, S object, Class<T> responseClass);

    <T> HttpResult<T, ? extends ErrorResponse> send(HttpRequest request);

    <T> HttpResult<T, ? extends ErrorResponse> send(HttpRequest request, Class<T> responseClass);

    default <T> HttpResult<T, ? extends ErrorResponse> get(Endpoint endpoint, Class<T> responseClass) {
        return send(endpoint, "GET", responseClass);
    }

    default <T> HttpResult<T, ? extends ErrorResponse> post(Endpoint endpoint, T object) {
        return send(endpoint, "POST", object);
    }

    default <S, T> HttpResult<T, ? extends ErrorResponse> post(Endpoint endpoint, S object, Class<T> responseClass) {
        return send(endpoint, "POST", object, responseClass);
    }

    default <T> HttpResult<T, ? extends ErrorResponse> put(Endpoint endpoint, T object) {
        return send(endpoint, "PUT", object);
    }

    default <T> HttpResult<T, ? extends ErrorResponse> patch(Endpoint endpoint, T object) {
        return send(endpoint, "PATCH", object);
    }

    default <T> HttpResult<T, ? extends ErrorResponse> delete(Endpoint endpoint) {
        return send(endpoint, "DELETE");
    }
}
