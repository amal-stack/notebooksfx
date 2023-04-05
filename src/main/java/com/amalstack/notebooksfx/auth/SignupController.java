package com.amalstack.notebooksfx.auth;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.css.StylesheetLocator;
import com.amalstack.notebooksfx.css.Stylesheets;
import com.amalstack.notebooksfx.data.model.User;
import com.amalstack.notebooksfx.data.model.UserRegistration;
import com.amalstack.notebooksfx.util.controls.Alerts;
import com.amalstack.notebooksfx.util.http.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class SignupController {
    private final AuthenticationService authenticationService;

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

    @FXML
    private ResourceBundle resources;

    private Command onSignUpSuccess;

    public SignupController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void initialize() {
        signUpButton.setOnAction(event -> signUp());
    }

    public void signUp() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        var result = authenticationService
                .registerUser(new UserRegistration(email,
                        name,
                        password,
                        confirmPassword), User.class);

        if (result.isSuccess()) {
            Alerts.builder()
                    .type(Alert.AlertType.INFORMATION)
                    .title(resources.getString("auth.signup.success.message.title"))
                    .header(resources.getString("auth.signup.success.message"))
                    .stylesheets(StylesheetLocator.getStylesheet(Stylesheets.ALERT))
                    .build()
                    .showAndWait();
            clearFields();
            CommandExecutor.execute(onSignUpSuccess);
            return;
        }

        result.getError().ifPresent(error -> Alerts.builder()
                .type(Alert.AlertType.ERROR)
                .title(resources.getString("auth.signup.error.message.title"))
                .header(resources.getString("auth.signup.error.message"))
                .message(AuthenticationErrorResponse.getErrorMessage(error, resources))
                .expandableContent(AuthenticationErrorResponse.createErrorNode(error, resources))
                .stylesheets(StylesheetLocator.getStylesheet(Stylesheets.ALERT))
                .build()
                .showAndWait());
    }

    public void setOnSignUpSuccess(Command onSignUpSuccess) {
        this.onSignUpSuccess = onSignUpSuccess;
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
}

