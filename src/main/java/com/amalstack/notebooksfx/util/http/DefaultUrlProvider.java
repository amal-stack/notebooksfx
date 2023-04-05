package com.amalstack.notebooksfx.util.http;

import java.net.URI;
import java.net.URISyntaxException;

public class DefaultUrlProvider implements UrlProvider {
    private final RouteTable routeTable;
    private final String baseUrl;

    private static final String EMPTY_BASE_URL = "";
    private static UrlProvider empty;

    public DefaultUrlProvider(String baseUrl, RouteTable routeTable) {
        this.baseUrl = baseUrl;
        this.routeTable = routeTable;
    }

    public DefaultUrlProvider(String baseUrl) {
        this(baseUrl, RouteTable.empty());
    }

    public DefaultUrlProvider(RouteTable routeTable) {
        this(EMPTY_BASE_URL, routeTable);
    }

    @Override
    public RouteTable getRouteTable() {
        return routeTable;
    }

    @Override
    public URI getBaseUrl() {
        return createURI();
    }

    @Override
    public URI getEndpoint(Endpoint endpoint) {
        return createURI(endpoint);
    }

    private URI createURI() {
        try {
            return new URI(baseUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private URI createURI(Endpoint endpoint) {
        try {
            return new URI(endpoint.get(baseUrl, routeTable));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static UrlProvider empty() {
        if (empty == null) {
            empty = new DefaultUrlProvider(EMPTY_BASE_URL, RouteTable.empty());
        }
        return empty;
    }
}
