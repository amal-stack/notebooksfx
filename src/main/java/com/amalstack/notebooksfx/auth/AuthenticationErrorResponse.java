package com.amalstack.notebooksfx.auth;

import com.amalstack.notebooksfx.util.http.ErrorResponse;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.util.ResourceBundle;

public class AuthenticationErrorResponse {
    public static String getErrorMessage(ErrorResponse errorResponse, ResourceBundle resources) {
        return errorResponse.message().orElseGet(() -> switch (errorResponse.status()) {
            case 401 -> resources.getString("auth.error.401");
            case 403 -> resources.getString("auth.error.403");
            default -> errorResponse.error();
        });
    }

    public static Node createErrorNode(ErrorResponse errorResponse, ResourceBundle resources) {
        StringBuilder builder = new StringBuilder();

        errorResponse.errors()
                .forEach((key, value) -> builder
                        .append(key)
                        .append(": ")
                        .append(value)
                        .append("\n"));

        builder.append("\n");

        builder.append(resources.getString("auth.error.response.status"))
                .append(": ")
                .append(errorResponse.status())
                .append("\n");

        builder.append(resources.getString("auth.error.response.error"))
                .append(": ")
                .append(errorResponse.error())
                .append("\n");


        errorResponse.message()
                .ifPresent(message -> builder
                        .append(resources.getString("auth.error.response.message"))
                        .append(": ")
                        .append(message)
                        .append("\n"));

        errorResponse.path()
                .ifPresent(path -> builder
                        .append(resources.getString("auth.error.response.path"))
                        .append(": ")
                        .append(path)
                        .append("\n"));

        errorResponse.timestamp()
                .ifPresent(timestamp -> builder
                        .append(resources.getString("auth.error.response.timestamp"))
                        .append(": ")
                        .append(timestamp)
                        .append("\n"));

        var errorTextArea = new TextArea(builder.toString());
        errorTextArea.setEditable(false);
        errorTextArea.setWrapText(true);
        errorTextArea.getStyleClass().add("error-text-area");

        var errorScrollPane = new ScrollPane(errorTextArea);
        errorScrollPane.setFitToWidth(true);
        errorScrollPane.setFitToHeight(true);
        errorScrollPane.setPadding(new Insets(10));

        return errorScrollPane;
    }
}
