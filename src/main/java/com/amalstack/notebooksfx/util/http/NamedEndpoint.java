package com.amalstack.notebooksfx.util.http;

import java.text.MessageFormat;
import java.util.Arrays;

public final class NamedEndpoint extends Endpoint {
    private final RouteName routeName;
    private QueryParameterMap queryParameterMap;
    private Object[] pathParameters;

    NamedEndpoint(RouteName routeName) {
        this.routeName = routeName;
    }

    public NamedEndpoint pathParameters(Object... pathParameters) {
        this.pathParameters = Arrays.stream(pathParameters).map(Object::toString).toArray();
        return this;
    }

    public NamedEndpoint queryParameters(String... queryParameters) {
        this.queryParameterMap = QueryParameterMap.of(queryParameters);
        return this;
    }

    public NamedEndpoint queryParameters(QueryParameterMap queryParameterMap) {
        this.queryParameterMap = queryParameterMap;
        return this;
    }

    @Override
    public String get(RouteTable routeTable) {
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
