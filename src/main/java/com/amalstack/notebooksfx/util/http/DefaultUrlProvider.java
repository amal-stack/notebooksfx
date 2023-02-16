package com.amalstack.notebooksfx.util.http;

import java.net.URI;
import java.net.URISyntaxException;

public class DefaultUrlProvider implements UrlProvider {
    private final RouteTable routeTable;
    private final String baseUrl;

    public DefaultUrlProvider(String baseUrl, RouteTable routeTable) {
        this.baseUrl = baseUrl;
        this.routeTable = routeTable;
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

    @Override
    public URI getEndpoint(String endpoint) {
        return createURI(endpoint);
    }


    private URI createURI() {
        try {
            return new URI(baseUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private URI createURI(String route) {
        try {
            return new URI(baseUrl + route);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private URI createURI(Endpoint endpoint) {
        try {
            return new URI(baseUrl + endpoint.get(routeTable));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
