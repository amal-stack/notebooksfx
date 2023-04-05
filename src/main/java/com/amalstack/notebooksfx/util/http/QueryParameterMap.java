package com.amalstack.notebooksfx.util.http;

import java.util.HashMap;
import java.util.Map;

public class QueryParameterMap {
    private final Map<String, String> parameters = new HashMap<>();

    public static QueryParameterMap of(String... keysAndValues) {
        if (keysAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("keysAndValues must have an even number of elements");
        }
        QueryParameterMap queryParameters = new QueryParameterMap();
        for (int i = 0; i < keysAndValues.length; i += 2) {
            queryParameters.add(keysAndValues[i], keysAndValues[i + 1]);
        }
        return queryParameters;
    }

    private void add(String key, String value) {
        parameters.put(key, value);
    }

    public String get(String key) {
        return parameters.get(key);
    }

    public String get(String key, String defaultValue) {
        return parameters.getOrDefault(key, defaultValue);
    }

    public boolean containsQuery(String key) {
        return parameters.containsKey(key);
    }

    public int size() {
        return parameters.size();
    }

    public Map<String, String> asMap() {
        return parameters;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("?");
        int size = parameters.size();
        for (var entry : parameters.entrySet()) {
            b.append(entry.getKey()).append("=").append(entry.getValue());
            if (--size > 0) {
                b.append("&");
            }
        }
        return b.toString();
    }
}

