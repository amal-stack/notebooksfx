package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;
import com.amalstack.notebooksfx.data.model.User;
import com.amalstack.notebooksfx.data.model.UserRegistration;
import com.amalstack.notebooksfx.data.repository.http.RouteNames;
import com.amalstack.notebooksfx.util.JsonMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Base64;

public class HttpBasicAuthenticationService implements AuthenticationService {
    private final AuthenticationContext authenticationContext;
    private final HttpClientService httpClient;
    private final EndpointProvider endpointProvider;
    private final JsonMapper jsonMapper;

    public HttpBasicAuthenticationService(
            AuthenticationContext authenticationContext,
            HttpClientService httpClient,
            EndpointProvider endpointProvider,
            JsonMapper jsonMapper) {
        this.authenticationContext = authenticationContext;
        this.httpClient = httpClient;
        this.endpointProvider = endpointProvider;
        this.jsonMapper = jsonMapper;
    }


    public Result registerUser(UserRegistration user) {
        var request = HttpRequest.newBuilder()
                .uri(endpointProvider.getEndpoint(RouteNames.USERS))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonMapper.toJson(user)))
                .build();

        var result = httpClient.send(request, User.class);
        if (result.isSuccess()) {
            return Result.success(result.getObject().get());
        }

        ErrorResponse error = result.getError().get();
        return Result.failure(error);
    }

    @Override
    public Result authenticate(String username, char[] password) {
        authenticationContext.clearAuthentication();
        String authorizationHeader = HttpBasicAuthorizationHeader.create(username, password);
        Arrays.fill(password, ' ');

        var request = HttpRequest.newBuilder()
                .uri(endpointProvider.getEndpoint(RouteName.of(RouteNames.USERS)))
                .header("Authorization", authorizationHeader)
                .GET()
                .build();

        var result = httpClient.send(request, User.class);
        if (result.isSuccess()) {
            User user = result.getObject().orElseThrow();
            authenticationContext.setAuthentication(Authentication.forUser(user, authorizationHeader));
            return Result.success(user);
        }

        authenticationContext.setAuthentication(Authentication.ANONYMOUS);

        ErrorResponse error = result.getError().orElseThrow();
        return Result.failure(error);
    }


//    @Override
//    public Result registerUser(UserRegistration user) {
//        var request = HttpRequest.newBuilder()
//                .uri(endpointProvider.getEndpoint(RouteNames.USERS))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(jsonMapper.toJson(user)))
//                .build();
//
//        var response = httpClient.send(request, User.class);
//        if (HttpResponseUtil.isSuccessful(response)) {
//            User createdUser = jsonMapper.fromJson(response.body(), User.class);
//            return Result.success(createdUser);
//        }
//
//        ErrorResponse error = jsonMapper.fromJson(response.body(), ErrorResponse.class);
//        return Result.failure(error);
//    }

//    @Override
//    public Result authenticate(String username, char[] password) {
//        String authorizationHeader = HttpBasicAuthorizationHeader.create(username, password);
//        Arrays.fill(password, ' ');
//
//        var request = HttpRequest.newBuilder()
//                .uri(endpointProvider.getEndpoint(RouteName.of(RouteNames.USERS)))
//                .header("Authorization", authorizationHeader)
//                .GET()
//                .build();
//
//        HttpResponse<String> response = sendHttpRequest(request);
//        if (HttpResponseUtil.isSuccessful(response)) {
//            User user = jsonMapper.fromJson(response.body(), User.class);
//            authentication = Authentication.forUser(user, authorizationHeader);
//
//            return Result.success(user);
//        }
//        String body = response.body();
//        if (body == null || body.isBlank()) {
//
//        }
//        authentication = Authentication.ANONYMOUS;
//
//        ErrorResponse error = jsonMapper.fromJson(response.body(), ErrorResponse.class);
//        return Result.failure(error);
//    }


    private HttpResponse<String> sendHttpRequest(HttpRequest request) {
        var client = HttpClient.newHttpClient();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    static class HttpBasicAuthorizationHeader {
        public static String PREFIX = "Basic ";

        private HttpBasicAuthorizationHeader() {
        }

        public static String create(String username, char[] password) {
            return PREFIX + Base64
                    .getEncoder()
                    .encodeToString((username + ":" + new String(password)).getBytes());
        }
    }
}

