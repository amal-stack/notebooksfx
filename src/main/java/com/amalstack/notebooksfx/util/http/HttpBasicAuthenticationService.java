package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.AppRouteNames;
import com.amalstack.notebooksfx.util.BodyMapper;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.Base64;

public class HttpBasicAuthenticationService implements AuthenticationService {
    private final AuthenticationContext authenticationContext;
    private final HttpClientService httpClient;
    private final UrlProvider urlProvider;
    private final BodyMapper bodyMapper;

    public HttpBasicAuthenticationService(
            AuthenticationContext authenticationContext,
            HttpClientService httpClient,
            UrlProvider urlProvider,
            BodyMapper bodyMapper) {
        this.authenticationContext = authenticationContext;
        this.httpClient = httpClient;
        this.urlProvider = urlProvider;
        this.bodyMapper = bodyMapper;
    }


    public <T, U> Result<U, ? extends ErrorResponse> registerUser(T user, Class<U> userType) {
        var request = HttpRequest.newBuilder()
                .uri(urlProvider.getEndpoint(Endpoint.named(AppRouteNames.USERS)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(bodyMapper.toBody(user)))
                .build();

        HttpResult<U, ? extends ErrorResponse> result = httpClient.send(request, userType);
        if (result.isSuccess()) {
            return Result.success(result.getObject().orElseThrow());
        }

        ErrorResponse error = result.getError().orElseThrow();
        return Result.failure(error);
    }

    @Override
    public <U> Result<U, ? extends ErrorResponse> authenticate(String username, char[] password, Class<U> userType) {
        authenticationContext.clearAuthentication();
        String authorizationHeader = HttpBasicAuthorizationHeader.create(username, password);
        Arrays.fill(password, ' ');

        var request = HttpRequest.newBuilder()
                .uri(urlProvider.getEndpoint(Endpoint.named(AppRouteNames.USERS)))
                .header("Authorization", authorizationHeader)
                .GET()
                .build();

        HttpResult<U, ? extends ErrorResponse> result = httpClient.send(request, userType);
        if (result.isSuccess()) {
            U user = result.getObjectOrThrow();
            authenticationContext.setAuthentication(Authentication.forUser(user, authorizationHeader));
            return Result.success(user);
        }

        authenticationContext.setAuthentication(Authentication.ANONYMOUS);

        ErrorResponse error = result.getError().orElseThrow();
        return Result.failure(error);
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
