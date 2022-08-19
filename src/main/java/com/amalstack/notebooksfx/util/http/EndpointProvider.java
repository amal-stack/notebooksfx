package com.amalstack.notebooksfx.util.http;

import java.net.URI;

public interface EndpointProvider {
    RouteTable getRouteTable();

    URI getBaseUrl();

    URI getEndpoint(RouteName routeName);

    URI getEndpoint(String routeName);

    URI getEndpoint(String... routeNames);

    URI getEndpoint(RouteName routeName, QueryParameterMap parameters);

    URI getEndpoint(String routeName, QueryParameterMap parameters);

    URI getEndpoint(String[] routeNames, QueryParameterMap parameters);
}
