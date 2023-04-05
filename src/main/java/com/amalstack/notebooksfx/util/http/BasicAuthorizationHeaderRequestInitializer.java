package com.amalstack.notebooksfx.util.http;

import java.util.HashMap;
import java.util.Map;

public class BasicAuthorizationHeaderRequestInitializer implements HttpRequestInitializer {

    private final AuthenticationContext context;

    public BasicAuthorizationHeaderRequestInitializer(AuthenticationContext context) {
        this.context = context;
    }

    @Override
    public Map<String, String> headers() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        context.getAuthentication()
                .getAuthorizationHeader()
                .ifPresent(header -> headers.put("Authorization", header));
        return headers;
    }
}
