package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.util.BodyMapper;

import java.net.http.HttpResponse;

public class DefaultErrorResponseFactory implements ErrorResponseFactory {

    private final BodyMapper bodyMapper;

    public DefaultErrorResponseFactory(BodyMapper bodyMapper) {
        this.bodyMapper = bodyMapper;
    }


    @Override
    public DefaultErrorResponse createErrorResponse(HttpResponse<String> response) {
        String body = response.body();
        if (body == null || body.isEmpty()) {
            return DefaultErrorResponse.fromResponse(response);
        }
        return bodyMapper.fromBody(body, DefaultErrorResponse.class);
    }
}
