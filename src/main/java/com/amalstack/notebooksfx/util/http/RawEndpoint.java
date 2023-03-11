package com.amalstack.notebooksfx.util.http;

public final class RawEndpoint extends Endpoint {
    private final String endpoint;

    RawEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String get(RouteTable routeTable) {
        return endpoint;
    }
}
