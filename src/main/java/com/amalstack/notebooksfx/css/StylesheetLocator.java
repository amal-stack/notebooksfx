package com.amalstack.notebooksfx.css;

import java.util.Objects;

public interface StylesheetLocator {
    static String getStylesheet(String name) {
        return Objects.requireNonNull(StylesheetLocator.class.getResource(name)).toExternalForm();
    }
}

