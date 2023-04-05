package com.amalstack.notebooksfx.views;


import java.net.URL;
import java.util.Objects;

public interface FxmlLocator {
    static URL getFxmlUrl(String fxmlName) {
        Objects.requireNonNull(fxmlName, "fxmlName cannot be null");

        if (!fxmlName.endsWith(".fxml")) {
            fxmlName += ".fxml";
        }

        return Objects.requireNonNull(FxmlLocator.class.getResource(fxmlName));
    }
}
