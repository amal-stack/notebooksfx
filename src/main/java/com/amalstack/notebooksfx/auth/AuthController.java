package com.amalstack.notebooksfx.auth;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
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
    private ProgressIndicator authProgressIndicator;

    @FXML
    private LoginController loginViewController;

    @FXML
    private SignupController signUpViewController;

    public void initialize() {
        signUpViewController.setOnSignUpSuccess(this::onSignUpSuccess);
        authProgressIndicator.visibleProperty()
                .bind(loginViewController.taskInProgressProperty()
                        .or(signUpViewController.taskInProgressProperty()));
    }

    private void onSignUpSuccess() {
        // Select Login tab
        actionTabPane.getSelectionModel().select(loginTab);
    }
}
