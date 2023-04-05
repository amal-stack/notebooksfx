package com.amalstack.notebooksfx.util.http;

import java.net.http.HttpClient;

@FunctionalInterface
public interface HttpClientInitializer {
    static HttpClientInitializer empty() {
        return builder -> {
        };
    }

    void initialize(HttpClient.Builder builder);
}

