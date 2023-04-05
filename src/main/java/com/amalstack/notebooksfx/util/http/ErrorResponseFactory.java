package com.amalstack.notebooksfx.util.http;

import java.net.http.HttpResponse;

@FunctionalInterface
public interface ErrorResponseFactory {

    ErrorResponse createErrorResponse(HttpResponse<String> response);

}
