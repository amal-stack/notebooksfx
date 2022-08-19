package com.amalstack.notebooksfx.util.http;

public class RouteName {
    private final String[] routeNames;

    private RouteName(String... routeNames) {
        this.routeNames = routeNames;
    }

    public static RouteName of(String... routeNames) {
        return new RouteName(routeNames);
    }

    public String[] asArray() {
        return routeNames;
    }
}
