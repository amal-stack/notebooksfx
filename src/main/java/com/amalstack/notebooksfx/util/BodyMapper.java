package com.amalstack.notebooksfx.util;

public interface BodyMapper {
    <T> T fromBody(String body, Class<T> type);

    <T> String toBody(T object);
}

