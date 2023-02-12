package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;
import com.amalstack.notebooksfx.util.JsonMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Function;


public class BasicHttpClientService implements HttpClientService {
    private final AuthenticationContext authenticationContext;
    private final EndpointProvider endpointProvider;
    private final JsonMapper jsonMapper;
    private final ErrorResponseTypeProvider errorResponseTypeProvider;

    public BasicHttpClientService(
            AuthenticationContext authenticationContext,
            EndpointProvider endpointProvider,
            JsonMapper jsonMapper,
            ErrorResponseTypeProvider errorResponseTypeProvider
    ) {
        this.authenticationContext = authenticationContext;
        this.endpointProvider = endpointProvider;
        this.jsonMapper = jsonMapper;
        this.errorResponseTypeProvider = errorResponseTypeProvider;
    }


    @Override
    public <T> HttpResult<T, ? extends ErrorResponse> send(RouteName routeName, String method) {
        HttpRequest request = createHttpRequest(routeName, method);
        return send(request);
    }

    @Override
    public <T> HttpResult<T, ? extends ErrorResponse> send(RouteName routeName, String method, Class<T> responseClass) {
        HttpRequest request = createHttpRequest(routeName, method);
        return send(request, responseClass);
    }

    @Override
    public <T> HttpResult<T, ? extends ErrorResponse> send(RouteName routeName, String method, T object) {
//        Class<T> type = (Class<T>) object.getClass();
//        return send(routeName, method, object, type);
        HttpRequest request = createHttpRequest(routeName, method, object);
        return send(request);
    }

    @Override
    public <T> HttpResult<T, ? extends ErrorResponse> send(RouteName routeName, String method, T object, Class<T> responseClass) {
        HttpRequest request = createHttpRequest(routeName, method, object);
        return send(request, responseClass);
    }


    @Override
    public <T> HttpResult<T, ? extends ErrorResponse> send(HttpRequest request, Class<T> responseClass) {
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
    public <T> HttpResult<T, ? extends ErrorResponse> send(HttpRequest request) {
        try {
            return createHttpResult(
                    HttpClient
                            .newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString()));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
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

        Authentication<?> auth = authenticationContext.getAuthentication();
        auth.getAuthorizationHeader()
                .ifPresent(header -> builder
                        .header("Authorization", header));

        return builder;
    }

    private <T> HttpResult<T, ? extends ErrorResponse> createHttpResult(HttpResponse<String> response) {
        ResponseStatus status = ResponseStatus.of(response.statusCode());

        if (status != ResponseStatus.SUCCESS) {
            return createErrorResult(response,
                    errorResponseTypeProvider.errorType(),
                    errorResponseTypeProvider.errorFactory());
        }
        return HttpResult.empty(response);
    }

    private <T> HttpResult<T, ? extends ErrorResponse> createHttpResult(
            HttpResponse<String> response,
            Class<T> type) {
        String body = response.body();
        ResponseStatus status = ResponseStatus.of(response.statusCode());

        if (status == ResponseStatus.SUCCESS) {
            if (body != null && !body.isBlank()) {
                return HttpResult.ofObject(response, jsonMapper.fromJson(body, type));
            }
            return HttpResult.empty(response);
        } else return createErrorResult(response,
                errorResponseTypeProvider.errorType(),
                errorResponseTypeProvider.errorFactory());
    }

    private <T> HttpResult<T, ? extends ErrorResponse> createErrorResult(
            HttpResponse<String> response,
            Class<? extends ErrorResponse> errorType,
            Function<HttpResponse<String>, ? extends ErrorResponse> errorFactory) {

        String body = response.body();
        if (body == null || body.isBlank()) {
            return HttpResult.ofError(response, errorFactory.apply(response));
        }
        return HttpResult.ofError(response, jsonMapper.fromJson(body, errorType));
    }
}


