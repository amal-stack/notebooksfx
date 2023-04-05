package com.amalstack.notebooksfx.util.controls;

import javafx.scene.Node;
import javafx.scene.control.Alert;

public class AlertBuilder {

    private String title;
    private String message;
    private Alert.AlertType type;
    private String header;
    private Node expandableContent;
    private String[] stylesheets;

    AlertBuilder() {

    }

    public AlertBuilder type(Alert.AlertType type) {
        this.type = type;
        return this;
    }

    public AlertBuilder title(String title) {
        this.title = title;
        return this;
    }

    public AlertBuilder message(String message) {
        this.message = message;
        return this;
    }

    public AlertBuilder header(String header) {
        this.header = header;
        return this;
    }

    public AlertBuilder expandableContent(Node expandableContent) {
        this.expandableContent = expandableContent;
        return this;
    }

    public AlertBuilder stylesheets(String... stylesheets) {
        this.stylesheets = stylesheets;
        return this;
    }

    public Alert build() {
        Alert alert = new Alert(type, message);
        alert.setTitle(title);
        alert.setHeaderText(header);
        if (expandableContent != null) {
            alert.getDialogPane().setExpandableContent(expandableContent);
        }
        if (stylesheets != null) {
            alert.getDialogPane().getStylesheets().addAll(stylesheets);
        }
        return alert;
    }
}
