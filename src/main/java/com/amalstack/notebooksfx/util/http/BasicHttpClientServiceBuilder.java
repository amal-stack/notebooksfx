package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.util.BodyMapper;
import com.amalstack.notebooksfx.util.JacksonMapper;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BasicHttpClientServiceBuilder {
    private final Map<String, String> headers = new HashMap<>();
    private HttpClientInitializer httpClientInitializer;
    private BodyMapper bodyMapper;
    private ErrorResponseFactory errorResponseFactory;
    private Consumer<HttpRequest.Builder> requestBuilderConfig;
    private String baseUri;
    private RouteTable routeTable;

    private BasicHttpClientServiceBuilder() {
    }

    public static BasicHttpClientServiceBuilder create() {
        return new BasicHttpClientServiceBuilder();
    }

    public BasicHttpClientServiceBuilder client(HttpClientInitializer httpClientInitializer) {
        this.httpClientInitializer = httpClientInitializer;
        return this;
    }

    public BasicHttpClientServiceBuilder request(Consumer<HttpRequest.Builder> requestBuilderConfig) {
        this.requestBuilderConfig = requestBuilderConfig;
        return this;
    }

    public BasicHttpClientServiceBuilder header(String header, String value) {
        headers.put(header, value);
        return this;
    }

    public BasicHttpClientServiceBuilder headers(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public BasicHttpClientServiceBuilder baseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public BasicHttpClientServiceBuilder routes(RouteTable routeTable) {
        this.routeTable = routeTable;
        return this;
    }

    public BasicHttpClientServiceBuilder mapper(BodyMapper bodyMapper) {
        this.bodyMapper = bodyMapper;
        return this;
    }

    public BasicHttpClientServiceBuilder errorResponseFactory(ErrorResponseFactory errorResponseFactory) {
        this.errorResponseFactory = errorResponseFactory;
        return this;
    }

    public HttpClientService build() {

        UrlProvider urlProvider = Defaults.urlProvider(baseUri, routeTable);

        HttpRequestInitializer httpRequestBuilderInitializer = Defaults.requestInitializer(requestBuilderConfig, headers);

        if (httpClientInitializer == null) {
            httpClientInitializer = Defaults.clientInitializer();
        }

        if (bodyMapper == null) {
            bodyMapper = Defaults.bodyMapper();
        }

        if (errorResponseFactory == null) {
            errorResponseFactory = Defaults.errorResponseFactory(bodyMapper);
        }


        return new BasicHttpClientService(
                httpRequestBuilderInitializer,
                httpClientInitializer,
                urlProvider,
                bodyMapper,
                errorResponseFactory
        );
    }

    static class Defaults {

        static UrlProvider urlProvider(String baseUri, RouteTable routeTable) {
            if (baseUri == null && RouteTable.isNullOrEmpty(routeTable)) {
                System.out.println("Warning: Using empty UrlProvider since base URI is null and RouteTable is null or is empty. Only absolute URLs are available.");
                return DefaultUrlProvider.empty();
            } else if (baseUri == null) {
                System.out.println("Base URI is null. Relative URLs are not available.");
                return new DefaultUrlProvider(routeTable);
            } else if (RouteTable.isNullOrEmpty(routeTable)) {
                System.out.println("Warning: RouteTable is null or is empty. Named endpoints are not available.");
                return new DefaultUrlProvider(baseUri);
            } else {
                return new DefaultUrlProvider(baseUri, routeTable);
            }
        }

        static HttpClientInitializer clientInitializer() {
            return HttpClientInitializer.empty();
        }

        static HttpRequestInitializer requestInitializer(Consumer<HttpRequest.Builder> requestBuilderConfig, Map<String, String> headers) {
            if (requestBuilderConfig == null && headers.isEmpty()) {
                return HttpRequestInitializer.empty();
            }
            if (requestBuilderConfig == null) {
                return HttpRequestInitializer.ofHeaders(headers);
            }
            if (headers.isEmpty()) {
                return HttpRequestInitializer.of(requestBuilderConfig);
            }
            return HttpRequestInitializer.of(requestBuilderConfig, headers);
        }

        static BodyMapper bodyMapper() {
            return new JacksonMapper();
        }

        static ErrorResponseFactory errorResponseFactory(BodyMapper mapper) {
            return new DefaultErrorResponseFactory(mapper);
        }
    }
}
