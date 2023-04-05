package com.amalstack.notebooksfx.util.http;

import java.net.http.HttpRequest;
import java.util.Map;
import java.util.function.Consumer;

public interface HttpRequestInitializer {
    static HttpRequestInitializer empty() {
        return new HttpRequestInitializer() {
        };
    }

    static HttpRequestInitializer ofHeaders(Map<String, String> headers) {
        return new HttpRequestInitializer() {
            @Override
            public Map<String, String> headers() {
                return headers;
            }
        };
    }

    static HttpRequestInitializer of(Consumer<HttpRequest.Builder> requestBuilderConfig) {
        return new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest.Builder builder) {
                if (requestBuilderConfig != null) {
                    requestBuilderConfig.accept(builder);
                }
            }
        };
    }

    static HttpRequestInitializer of(Consumer<HttpRequest.Builder> requestBuilderConfig,
                                     Map<String, String> headers) {
        return new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest.Builder builder) {
                if (requestBuilderConfig != null) {
                    requestBuilderConfig.accept(builder);
                }
            }

            @Override
            public Map<String, String> headers() {
                return headers;
            }
        };
    }

    default void initialize(HttpRequest.Builder builder) {
    }

    default Map<String, String> headers() {
        return Map.of();
    }
}

