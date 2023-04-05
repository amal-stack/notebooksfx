package com.amalstack.notebooksfx.util.http;

import java.text.MessageFormat;
import java.util.Arrays;

public abstract class EndpointBase<E extends Endpoint> extends Endpoint {
    private Object[] pathParameters;
    private QueryParameterMap queryParameterMap;

    public E pathParameters(Object... pathParameters) {
        this.pathParameters = Arrays.stream(pathParameters)
                .map(Object::toString)
                .toArray();
        return self();
    }

    public E queryParameters(String... queryParameters) {
        this.queryParameterMap = QueryParameterMap.of(queryParameters);
        return self();
    }

    public E queryParameters(QueryParameterMap queryParameterMap) {
        this.queryParameterMap = queryParameterMap;
        return self();
    }

    protected String appendQueryParameters(String path) {
        if (queryParameterMap != null) {
            path += queryParameterMap.toString();
        }
        return path;
    }

    protected String appendPathParameters(String path) {
        if (pathParameters != null && pathParameters.length != 0) {
            path = MessageFormat.format(path, pathParameters);
        }
        return path;
    }

    @SuppressWarnings("unchecked")
    private E self() {
        return (E) this;
    }
}
