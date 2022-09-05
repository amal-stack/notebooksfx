package com.amalstack.notebooksfx.util.http;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(String routeName) {
        super("Route not found: " + routeName);
    }
}
