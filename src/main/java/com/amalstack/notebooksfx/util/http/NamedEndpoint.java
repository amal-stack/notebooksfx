package com.amalstack.notebooksfx.util.http;

public final class NamedEndpoint extends EndpointBase<NamedEndpoint> {
    private final RouteName routeName;

    NamedEndpoint(RouteName routeName) {
        this.routeName = routeName;
    }

    @Override
    public String get(String baseUrl, RouteTable routeTable) {
        throwIfInvalid(baseUrl, routeTable);

        String path = routeTable.get(routeName);

        path = appendPathParameters(path);
        path = appendQueryParameters(path);

        return baseUrl + path;
    }

    static void throwIfInvalid(String baseUrl, RouteTable routeTable) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new EndpointException("Named relative endpoints require a base URL. Only absolute and named absolute endpoints can be used without a base URL.");
        }
        if (RouteTable.isNullOrEmpty(routeTable)) {
            throw new EndpointException("Named endpoints require a route table. Only absolute and relative endpoints can be used without a route table.");
        }
    }
}

