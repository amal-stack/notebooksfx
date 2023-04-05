package com.amalstack.notebooksfx.util.http;

import java.net.http.HttpClient;

@FunctionalInterface
public interface HttpClientInitializer {
    void initialize(HttpClient.Builder builder);

    static HttpClientInitializer empty() {
        return builder -> {
        };
    }
}

