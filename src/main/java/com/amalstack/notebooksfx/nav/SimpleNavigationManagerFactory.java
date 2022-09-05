package com.amalstack.notebooksfx.nav;

import javafx.stage.Stage;

public class SimpleNavigationManagerFactory {
    public static SimpleNavigationManager create(Stage stage) {
        return new SimpleNavigationManager(stage);
    }
}
