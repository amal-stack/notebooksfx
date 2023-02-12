package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;

import java.net.http.HttpResponse;
import java.util.function.Function;

public class DefaultErrorResponseTypeProvider implements ErrorResponseTypeProvider {
    @Override
    public Class<ErrorResponse> errorType() {
        return ErrorResponse.class;
    }

    @Override
    public Function<HttpResponse<String>, ErrorResponse> errorFactory() {
        return ErrorResponse::fromResponse;
    }
}