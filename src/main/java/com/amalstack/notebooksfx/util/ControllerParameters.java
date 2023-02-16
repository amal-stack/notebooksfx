package com.amalstack.notebooksfx.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerParameters {
    private final Map<String, Object> parameters = new HashMap<>();

    public void set(String parameter, Object value) {
        parameters.put(parameter, value);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(String parameter) {
        return (Optional<T>) Optional.ofNullable(parameters.get(parameter));
    }

}
