package com.amalstack.notebooksfx.util;

public interface JsonMapper {
    <T> T fromJson(String json, Class<T> type);

    <T> String toJson(T object);
}

