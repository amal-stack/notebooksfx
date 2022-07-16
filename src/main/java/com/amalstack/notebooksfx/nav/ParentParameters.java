package com.amalstack.notebooksfx.nav;

import javafx.util.Callback;

import java.net.URL;

public record ParentParameters(
        String name,
        URL url,
        String title,
        Callback<Class<?>, Object> controllerFactory
) {
}
