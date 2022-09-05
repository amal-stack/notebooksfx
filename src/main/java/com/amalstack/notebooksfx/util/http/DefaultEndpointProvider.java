package com.amalstack.notebooksfx.util.http;

import java.net.URI;
import java.net.URISyntaxException;

public class DefaultEndpointProvider implements EndpointProvider {
    private final RouteTable routeTable;
    private final String baseUrl;

    public DefaultEndpointProvider(String baseUrl, RouteTable routeTable) {
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
    public URI getEndpoint(String routeName) {
        return createURI(routeTable.get(routeName));
    }

    @Override
    public URI getEndpoint(String... routeNames) {
        return createURI(routeTable.get(routeNames));
    }

    @Override
    public URI getEndpoint(RouteName routeName) {
        return createURI(routeTable.get(routeName));
    }

    @Override
    public URI getEndpoint(String routeName, QueryParameterMap parameters) {
        return createURI(routeTable.get(routeName), parameters);
    }

    @Override
    public URI getEndpoint(String[] routeNames, QueryParameterMap parameters) {
        return createURI(routeTable.get(routeNames), parameters);
    }

    @Override
    public URI getEndpoint(RouteName routeName, QueryParameterMap parameters) {
        return createURI(routeTable.get(routeName), parameters);
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

    private URI createURI(String route, QueryParameterMap parameters) {
        try {
            return new URI(baseUrl + route + parameters.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}





