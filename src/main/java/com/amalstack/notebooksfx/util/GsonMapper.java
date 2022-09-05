package com.amalstack.notebooksfx.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.function.Consumer;

public class GsonMapper implements JsonMapper {

    private final Gson gson;

    public GsonMapper(Gson gson) {
        this.gson = gson;
    }

    public GsonMapper() {
        this(new Gson());
    }

    @Override
    public <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    @Override
    public <T> String toJson(T object) {
        return gson.toJson(object);
    }

    public static class Factory {
        public static GsonMapper create(Consumer<GsonBuilder> config) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            config.accept(gsonBuilder);
            return new GsonMapper(gsonBuilder.create());
        }
    }
}
