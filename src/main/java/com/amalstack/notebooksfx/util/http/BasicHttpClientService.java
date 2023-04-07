package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.util.BodyMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;


public class BasicHttpClientService implements HttpClientService, AsyncHttpClientService {
    protected final HttpClientInitializer httpClientInitializer;
    protected final UrlProvider urlProvider;
    protected final BodyMapper bodyMapper;
    protected final ErrorResponseFactory errorResponseFactory;
    protected final HttpRequestInitializer httpRequestBuilderInitializer;

    public BasicHttpClientService(
            HttpRequestInitializer httpRequestBuilderInitializer,
            HttpClientInitializer httpClientInitializer,
            UrlProvider urlProvider,
            BodyMapper bodyMapper,
            ErrorResponseFactory errorResponseFactory
    ) {
        this.httpRequestBuilderInitializer = httpRequestBuilderInitializer;
        this.httpClientInitializer = httpClientInitializer;
        this.urlProvider = urlProvider;
        this.bodyMapper = bodyMapper;
        this.errorResponseFactory = errorResponseFactory;
    }

    @Override
    public <T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method) {
        HttpRequest request = createHttpRequest(endpoint, method);
        return send(request);
    }

    @Override
    public <T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method, Class<T> responseClass) {
        HttpRequest request = createHttpRequest(endpoint, method);
        return send(request, responseClass);
    }

    @Override
    public <T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method, T object) {
        HttpRequest request = createHttpRequest(endpoint, method, object);
        return send(request);
    }

    @Override
    public <S, T> HttpResult<T, ? extends ErrorResponse> send(Endpoint endpoint, String method, S object, Class<T> responseClass) {
        HttpRequest request = createHttpRequest(endpoint, method, object);
        return send(request, responseClass);
    }

    @Override
    public <T> HttpResult<T, ? extends ErrorResponse> send(HttpRequest request) {
        try {
            return createHttpResult(
                    createHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString()));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> HttpResult<T, ? extends ErrorResponse> send(HttpRequest request, Class<T> responseClass) {
        try {
            return createHttpResult(
                    createHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString()),
                    responseClass);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(Endpoint endpoint, String method) {
        return sendAsync(createHttpRequest(endpoint, method));
    }

    @Override
    public <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(Endpoint endpoint, String method, T object) {
        return sendAsync(createHttpRequest(endpoint, method, object));
    }

    @Override
    public <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(Endpoint endpoint, String method, Class<T> responseClass) {
        return sendAsync(createHttpRequest(endpoint, method), responseClass);
    }

    @Override
    public <S, T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(Endpoint endpoint, String method, S object, Class<T> responseClass) {
        return sendAsync(createHttpRequest(endpoint, method, object), responseClass);
    }

    @Override
    public <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(HttpRequest request) {
        return createHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(this::createHttpResult);
    }

    @Override
    public <T> CompletableFuture<HttpResult<T, ? extends ErrorResponse>> sendAsync(HttpRequest request, Class<T> responseClass) {
        return createHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> createHttpResult(response, responseClass));
    }

    protected <T> HttpRequest createHttpRequest(Endpoint endpoint, String method, T object) {
        return createRequestBuilder(endpoint)
                .method(method, HttpRequest.BodyPublishers.ofString(bodyMapper.toBody(object)))
                .build();
    }

    protected HttpRequest createHttpRequest(Endpoint endpoint, String method) {
        return createRequestBuilder(endpoint)
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();
    }

    private HttpRequest.Builder createRequestBuilder(Endpoint endpoint) {
        var builder = HttpRequest.newBuilder()
                .uri(urlProvider.getEndpoint(endpoint));

        httpRequestBuilderInitializer.headers().forEach(builder::header);
        httpRequestBuilderInitializer.initialize(builder);

        return builder;
    }

    protected HttpClient createHttpClient() {
        var builder = HttpClient.newBuilder();
        httpClientInitializer.initialize(builder);
        return builder.build();
    }

    protected <T> HttpResult<T, ? extends ErrorResponse> createHttpResult(HttpResponse<String> response) {
        if (ResponseStatus.of(response.statusCode()) != ResponseStatus.SUCCESS) {
            return HttpResult.ofError(response, errorResponseFactory.createErrorResponse(response));
        }
        return HttpResult.empty(response);
    }

    protected <T> HttpResult<T, ? extends ErrorResponse> createHttpResult(
            HttpResponse<String> response,
            Class<T> type) {
        String body = response.body();
        ResponseStatus status = ResponseStatus.of(response.statusCode());

        if (status == ResponseStatus.SUCCESS) {
            return body != null && !body.isBlank()
                    ? HttpResult.ofObject(response, bodyMapper.fromBody(body, type))
                    : HttpResult.empty(response);
        }
        return HttpResult.ofError(response, errorResponseFactory.createErrorResponse(response));
    }
}


