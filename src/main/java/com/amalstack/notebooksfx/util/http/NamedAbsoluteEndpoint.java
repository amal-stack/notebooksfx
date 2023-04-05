package com.amalstack.notebooksfx.util.http;

public class NamedAbsoluteEndpoint extends EndpointBase<NamedAbsoluteEndpoint> {
    private final String baseUrl;
    private final RouteName routeName;

    public NamedAbsoluteEndpoint(String baseUrl, RouteName routeName) {
        this.baseUrl = baseUrl;
        this.routeName = routeName;
    }

    @Override
    public String get(String ignoredBaseUrl, RouteTable routeTable) {
        throwIfInvalid(baseUrl, routeTable);

        String path = routeTable.get(routeName);

        path = appendPathParameters(path);
        path = appendQueryParameters(path);

        return baseUrl + path;
    }

    static void throwIfInvalid(String baseUrl, RouteTable routeTable) {
        if (RouteTable.isNullOrEmpty(routeTable)) {
            throw new EndpointException("Named endpoints require a route table. Only absolute and relative endpoints can be used without a route table.");
        }
    }
}
