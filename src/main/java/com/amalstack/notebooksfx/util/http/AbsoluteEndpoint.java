package com.amalstack.notebooksfx.util.http;

public final class AbsoluteEndpoint extends EndpointBase<AbsoluteEndpoint> {
    private final String url;

    AbsoluteEndpoint(String url) {
        this.url = url;
    }

    @Override
    public String get(String ignoredBaseUrl, RouteTable routeTable) {
        String url = this.url;
        url = appendPathParameters(url);
        url = appendQueryParameters(url);
        return url;
    }
}

