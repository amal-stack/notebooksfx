package com.amalstack.notebooksfx.util.http;

import java.net.URI;

public interface UrlProvider {
    RouteTable getRouteTable();

    URI getBaseUrl();

    URI getEndpoint(Endpoint endpoint);

}
