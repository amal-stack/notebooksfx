package com.amalstack.notebooksfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class AuthController {

    @FXML
    private VBox signUpView;
    @FXML
    private VBox loginView;
    @FXML
    private Tab signUpTab;

    @FXML
    private Tab loginTab;

    @FXML
    private TabPane actionTabPane;

    @FXML
    private LoginController loginViewController;

    public void initialize() {
    }
}
