package com.amalstack.notebooksfx.util.controls;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerts {
    public static void showConfirmationAlert(String title, String message, Runnable onYes) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                message,
                ButtonType.YES,
                ButtonType.NO);
        alert.setTitle(title);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                onYes.run();
            }
        });
    }

    public static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                message,
                ButtonType.OK);
        alert.setTitle(title);

        alert.showAndWait();
    }

    public static void showInformationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                message,
                ButtonType.OK);
        alert.setTitle(title);

        alert.showAndWait();
    }
}
