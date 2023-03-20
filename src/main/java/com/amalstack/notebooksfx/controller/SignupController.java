package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.data.model.ErrorEntry;
import com.amalstack.notebooksfx.data.model.User;
import com.amalstack.notebooksfx.data.model.UserRegistration;
import com.amalstack.notebooksfx.util.http.AuthenticationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class SignupController {
    private final AuthenticationService authenticationService;

    private final ObservableList<ErrorEntry> signUpErrorEntries = FXCollections.observableArrayList();

    @FXML
    private ScrollPane signUpErrorScrollPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button signUpButton;

    public SignupController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void initialize() {
        //errorMessageLabel.setWrapText(true);
        signUpButton.setOnAction(event -> signUp());
        signUpErrorScrollPane.setContent(ErrorTableViewFactory.create(signUpErrorEntries, "signUpView"));
    }

    public void signUp() {
        // Hide and clear existing errors
        signUpErrorScrollPane.setVisible(false);
        signUpErrorEntries.clear();


        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        var result = authenticationService
                .registerUser(new UserRegistration(email, name, password, confirmPassword), User.class);

        if (result.isSuccess()) {
            return;
        }

        result.getError().ifPresent(error -> {
            signUpErrorEntries.addAll(ErrorEntry.fromError(error));
            signUpErrorScrollPane.setVisible(true);
        });
    }
}
