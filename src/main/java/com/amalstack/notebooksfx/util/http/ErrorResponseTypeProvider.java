package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;

import java.net.http.HttpResponse;
import java.util.function.Function;

public interface ErrorResponseTypeProvider {
    Class<? extends ErrorResponse> errorType();

    Function<HttpResponse<String>, ? extends ErrorResponse> errorFactory();
}
