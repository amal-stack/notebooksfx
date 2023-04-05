package com.amalstack.notebooksfx.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.function.Consumer;

public class GsonMapper implements BodyMapper {

    private final Gson gson;

    public GsonMapper(Gson gson) {
        this.gson = gson;
    }

    public GsonMapper() {
        this(new Gson());
    }

    public static GsonMapper create(Consumer<GsonBuilder> config) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        config.accept(gsonBuilder);
        return new GsonMapper(gsonBuilder.create());
    }

    @Override
    public <T> T fromBody(String body, Class<T> type) {
        return gson.fromJson(body, type);
    }

    @Override
    public <T> String toBody(T object) {
        return gson.toJson(object);
    }
}
