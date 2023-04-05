package com.amalstack.notebooksfx.util.http;

public class RelativeEndpoint extends EndpointBase<RelativeEndpoint> {
    private final String path;

    RelativeEndpoint(String path) {
        this.path = path;
    }

    @Override
    public String get(String baseUrl, RouteTable routeTable) {
        throwIfInvalid(baseUrl, routeTable);

        String path = this.path;

        path = appendPathParameters(path);
        path = appendQueryParameters(path);

        return baseUrl + path;
    }

    static void throwIfInvalid(String baseUrl, RouteTable routeTable) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new EndpointException("Relative endpoints require a base URL. Only absolute and named absolute endpoints can be used without a base URL.");
        }
    }
}
