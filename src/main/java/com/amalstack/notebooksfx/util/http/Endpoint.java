package com.amalstack.notebooksfx.util.http;

public abstract class Endpoint {

    public static NamedEndpoint named(String... routeNames) {
        return named(RouteName.of(routeNames));
    }

    public static NamedEndpoint named(RouteName routeName) {
        return new NamedEndpoint(routeName);
    }

    public static NamedAbsoluteEndpoint namedAbsolute(String baseUrl, String... routeNames) {
        return namedAbsolute(baseUrl, RouteName.of(routeNames));
    }

    public static NamedAbsoluteEndpoint namedAbsolute(String baseUrl, RouteName routeName) {
        return new NamedAbsoluteEndpoint(baseUrl, routeName);
    }

    public static RelativeEndpoint relative(String endpoint) {
        return new RelativeEndpoint(endpoint);
    }

    public static AbsoluteEndpoint absolute(String endpoint) {
        return new AbsoluteEndpoint(endpoint);
    }

    public abstract String get(String baseUrl, RouteTable routeTable);

}

