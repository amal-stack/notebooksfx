package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;

import java.net.http.HttpRequest;

public interface HttpClientService {

    <T> HttpResult<T, ? extends ErrorResponse> send(RouteName routeName, String method);

    <T> HttpResult<T, ? extends ErrorResponse> send(RouteName routeName, String method, T object);


    <T> HttpResult<T, ? extends ErrorResponse> send(RouteName routeName, String method, Class<T> responseClass);

    <T> HttpResult<T, ? extends ErrorResponse> send(RouteName routeName, String method, T object, Class<T> responseClass);

    <T> HttpResult<T, ? extends ErrorResponse> send(HttpRequest request, Class<T> responseClass);

    <T> HttpResult<T, ? extends ErrorResponse> send(HttpRequest request);


    default <T> HttpResult<T, ? extends ErrorResponse> get(RouteName routeName, Class<T> responseClass) {
        return send(routeName, "GET", responseClass);
    }

    default <T> HttpResult<T, ? extends ErrorResponse> post(RouteName routeName, T object) {
        return send(routeName, "POST", object);
    }

    default <T> HttpResult<T, ? extends ErrorResponse> put(RouteName routeName, T object) {
        return send(routeName, "PUT", object);
    }

    default <T> HttpResult<T, ? extends ErrorResponse> patch(RouteName routeName, T object) {
        return send(routeName, "PATCH", object);
    }

    default <T> HttpResult<T, ? extends ErrorResponse> delete(RouteName routeName) {
        return send(routeName, "DELETE");
    }
}
