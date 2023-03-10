package com.amalstack.notebooksfx.util.http;

public abstract class Endpoint {

    public static NamedEndpoint named(String... routeNames) {
        return new NamedEndpoint(RouteName.of(routeNames));
    }

    public static NamedEndpoint named(RouteName routeName) {
        return new NamedEndpoint(routeName);
    }

    public static RawEndpoint raw(String endpoint) {
        return new RawEndpoint(endpoint);
    }

    public abstract String get(RouteTable routeTable);


}
