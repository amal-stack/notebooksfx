package com.amalstack.notebooksfx;

import javafx.util.Callback;

import java.net.URL;

public record ParentParameters(
        String name,
        URL url,
        String title,
        Callback<Class<?>, Object> controllerFactory
) {
}
