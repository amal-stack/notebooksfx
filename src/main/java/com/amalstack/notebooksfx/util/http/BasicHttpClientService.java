package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;
import com.amalstack.notebooksfx.util.JsonMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class BasicHttpClientService implements HttpClientService {
    private final AuthenticationContext authenticationContext;
    private final EndpointProvider endpointProvider;
    private final JsonMapper jsonMapper;

    public BasicHttpClientService(
            AuthenticationContext authenticationContext,
            EndpointProvider endpointProvider,
            JsonMapper jsonMapper
    ) {
        this.authenticationContext = authenticationContext;
        this.endpointProvider = endpointProvider;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public <T> HttpResult<T> send(RouteName routeName, String method) {
        HttpRequest request = createHttpRequest(routeName, method);
        return send(request);
    }

    @Override
    public <T> HttpResult<T> send(RouteName routeName, String method, Class<T> responseClass) {
        HttpRequest request = createHttpRequest(routeName, method);
        return send(request, responseClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> HttpResult<T> send(RouteName routeName, String method, T object) {
        Class<T> type = (Class<T>) object.getClass();
        return send(routeName, method, object, type);
    }

    @Override
    public <T> HttpResult<T> send(RouteName routeName, String method, T object, Class<T> responseClass) {
        HttpRequest request = createHttpRequest(routeName, method, object);
        return send(request, responseClass);
    }


    @Override
    public <T> HttpResult<T> send(HttpRequest request, Class<T> responseClass) {
        try {
            return createHttpResult(
                    HttpClient
                            .newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString()),
                    responseClass);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> HttpResult<T> send(HttpRequest request) {
        try {
            return createHttpResult(
                    HttpClient
                            .newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString()));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private <T> HttpResult<T> createHttpResult(HttpResponse<String> response) {
        String body = response.body();
        ResponseStatus status = ResponseStatus.of(response.statusCode());

        var builder = HttpResult
                .<T>builder()
                .withResponse(response);

        if (status != ResponseStatus.SUCCESS) {
            addError(body, builder);
        }
        return builder.build();
    }

    private <T> HttpResult<T> createHttpResult(HttpResponse<String> response, Class<T> type) {
        String body = response.body();
        ResponseStatus status = ResponseStatus.of(response.statusCode());
        var builder = HttpResult
                .builder(type)
                .withResponse(response);

        if (status == ResponseStatus.SUCCESS) {
            if (body != null && !body.isBlank()) {
                builder.withObject(jsonMapper.fromJson(body, type));
            }
        } else addError(body, builder);

        return builder.build();
    }

    protected <T> HttpRequest createHttpRequest(RouteName routeName, String method, T object) {
        return createRequestBuilder(routeName)
                .method(method, HttpRequest.BodyPublishers.ofString(jsonMapper.toJson(object)))
                .build();
    }

    protected HttpRequest createHttpRequest(RouteName routeName, String method) {
        return createRequestBuilder(routeName)
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();
    }

    private HttpRequest.Builder createRequestBuilder(RouteName routeName) {

        var builder = HttpRequest.newBuilder()
                .uri(endpointProvider.getEndpoint(routeName))
                .header("Content-Type", "application/json");

        Authentication auth = authenticationContext.getAuthentication();
        auth.getAuthorizationHeader()
                .ifPresent(header -> builder
                        .header("Authorization", header));

        return builder;
    }

    private void addError(String body, HttpResult.Builder<?> builder) {
        if (body == null || body.isBlank()) {
            builder.withDefaultError();
            return;
        }
        builder.withError(jsonMapper.fromJson(body, ErrorResponse.class));
    }
}


