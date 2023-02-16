package com.amalstack.notebooksfx.util.http;

import java.text.MessageFormat;

public class Endpoint {
    private final RouteName routeName;
    private final QueryParameterMap queryParameterMap;
    private final Object[] pathParameters;
    private String endpoint;

    public Endpoint(RouteName routeName, Object[] pathParameters, QueryParameterMap queryParameterMap) {
        this.routeName = routeName;
        this.queryParameterMap = queryParameterMap;
        this.pathParameters = pathParameters;
    }

    public Endpoint(String endpoint) {
        this(null, null, null);
        this.endpoint = endpoint;
    }

    public static Endpoint of(String endpoint) {
        return new Endpoint(endpoint);
    }

    public static Endpoint ofName(String routeName) {
        return new Endpoint(RouteName.of(routeName), null, null);
    }

    public static Endpoint ofName(String... routeNames) {
        return new Endpoint(RouteName.of(routeNames), null, null);
    }

    public static Endpoint ofName(RouteName routeName) {
        return new Endpoint(routeName, null, null);
    }

    public static Endpoint ofName(RouteName routeName, Object... pathParameters) {
        return new Endpoint(routeName, pathParameters, null);
    }

    public static Endpoint ofName(RouteName routeName, QueryParameterMap queryParameterMap) {
        return new Endpoint(routeName, null, queryParameterMap);
    }

    public static Endpoint ofName(RouteName routeName, QueryParameterMap queryParameterMap, Object... pathParameters) {
        return new Endpoint(routeName, pathParameters, queryParameterMap);
    }

    public String get(RouteTable routeTable) {
        if (endpoint != null) {
            return endpoint;
        }
        String path = routeTable.get(routeName);
        if (pathParameters != null && pathParameters.length != 0) {
            path = MessageFormat.format(path, pathParameters);
        }
        if (queryParameterMap != null) {
            path += queryParameterMap.toString();
        }

        return path;
    }

}
