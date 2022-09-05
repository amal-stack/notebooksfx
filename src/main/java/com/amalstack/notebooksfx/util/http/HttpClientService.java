package com.amalstack.notebooksfx.util.http;

import java.net.http.HttpRequest;

public interface HttpClientService {

    <T> HttpResult<T> send(RouteName routeName, String method);

    <T> HttpResult<T> send(RouteName routeName, String method, Class<T> responseClass);

    <T> HttpResult<T> send(RouteName routeName, String method, T object);

    <T> HttpResult<T> send(RouteName routeName, String method, T object, Class<T> responseClass);

    <T> HttpResult<T> send(HttpRequest request, Class<T> responseClass);

    <T> HttpResult<T> send(HttpRequest request);


    default <T> HttpResult<T> get(RouteName routeName, Class<T> responseClass) {
        return send(routeName, "GET", responseClass);
    }

    default <T> HttpResult<T> post(RouteName routeName, T object) {
        return send(routeName, "POST", object);
    }

    default <T> HttpResult<T> put(RouteName routeName, T object) {
        return send(routeName, "PUT", object);
    }

    default <T> HttpResult<T> patch(RouteName routeName, T object) {
        return send(routeName, "PATCH", object);
    }

    default <T> HttpResult<T> delete(RouteName routeName) {
        return send(routeName, "DELETE");
    }
}
