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
}
