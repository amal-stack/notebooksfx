package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.data.model.ErrorEntry;
import com.amalstack.notebooksfx.nav.NavigationManager;
import com.amalstack.notebooksfx.nav.Parents;
import com.amalstack.notebooksfx.util.http.AuthenticationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class LoginController {
    private final AuthenticationService authenticationService;
    private final NavigationManager navigationManager;
    private final ObservableList<ErrorEntry> errorEntries = FXCollections.observableArrayList();

    @FXML
    private ScrollPane errorScrollPane;
    //    @FXML
//    private Label errorMessageLabel;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    public LoginController(AuthenticationService authenticationService, NavigationManager navigationManager) {
        this.authenticationService = authenticationService;
        this.navigationManager = navigationManager;
    }

    @FXML
    public void initialize() {

        loginButton.setOnAction(event -> login());
        errorScrollPane.setContent(ErrorTableViewFactory.create(errorEntries));
    }

    @FXML
    public void login() {
        errorScrollPane.setVisible(false);
        String email = emailField.getText();
        char[] password = passwordField.getText().toCharArray();
        var result = authenticationService.authenticate(email, password);
        if (result.isSuccess()) {
            navigationManager.navigateTo(Parents.HOME);
            return;
        }
        result.getError().ifPresent(error -> {
            errorEntries.addAll(ErrorEntry.fromError(error));
            errorScrollPane.setVisible(true);
        });
    }
}