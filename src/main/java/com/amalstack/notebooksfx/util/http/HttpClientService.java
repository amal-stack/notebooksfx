package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;

import java.net.http.HttpRequest;

public interface HttpClientService {

    <T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method);

    <T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method, T object);


    <T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method, Class<T> responseClass);

    <T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method, T object, Class<T> responseClass);

    <T> HttpResult<T, ? extends ErrorResponse> send(HttpRequest request, Class<T> responseClass);

    <T> HttpResult<T, ? extends ErrorResponse> send(HttpRequest request);


    default <T> HttpResult<T, ? extends ErrorResponse> get(Endpoint endpoint, Class<T> responseClass) {
        return send(endpoint, "GET", responseClass);
    }

    default <T> HttpResult<T, ? extends ErrorResponse> post(Endpoint endpoint, T object) {
        return send(endpoint, "POST", object);
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
