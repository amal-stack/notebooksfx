package com.amalstack.notebooksfx.auth;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.css.StylesheetLocator;
import com.amalstack.notebooksfx.css.Stylesheets;
import com.amalstack.notebooksfx.data.model.User;
import com.amalstack.notebooksfx.data.model.UserRegistration;
import com.amalstack.notebooksfx.util.Tasks;
import com.amalstack.notebooksfx.util.controls.Alerts;
import com.amalstack.notebooksfx.util.http.AuthenticationService;
import com.amalstack.notebooksfx.util.http.ErrorResponse;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class SignupController {
    private final AuthenticationService authenticationService;
    private final BooleanProperty taskInProgress = new SimpleBooleanProperty(false);
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

        var task = Tasks.wrap(() -> authenticationService
                        .registerUser(newUserRegistration(), User.class))
                .onSucceeded(this::processSignupResult)
                .throwOnFailed()
                .build();

        taskInProgress.bind(task.runningProperty());

        Tasks.execute(task);
    }

    public void setOnSignUpSuccess(Command onSignUpSuccess) {
        this.onSignUpSuccess = onSignUpSuccess;
    }

    public BooleanProperty taskInProgressProperty() {
        return taskInProgress;
    }

    private void processSignupResult(AuthenticationService.Result<User, ? extends ErrorResponse> result) {
        if (result.isSuccess()) {
            newSignupSuccessAlert().showAndWait();
            clearFields();
            CommandExecutor.execute(onSignUpSuccess);
            return;
        }

        result.getError()
                .map(this::newSignupErrorAlert)
                .ifPresent(Alert::showAndWait);
    }

    private Alert newSignupSuccessAlert() {
        return Alerts.builder()
                .type(Alert.AlertType.INFORMATION)
                .title(resources.getString("auth.signup.success.message.title"))
                .header(resources.getString("auth.signup.success.message"))
                .stylesheets(StylesheetLocator.getStylesheet(Stylesheets.ALERT))
                .build();
    }

    private Alert newSignupErrorAlert(ErrorResponse error) {
        return Alerts.builder()
                .type(Alert.AlertType.ERROR)
                .title(resources.getString("auth.signup.error.message.title"))
                .header(resources.getString("auth.signup.error.message"))
                .message(AuthenticationErrorResponse.getErrorMessage(error, resources))
                .expandableContent(AuthenticationErrorResponse.createErrorNode(error, resources))
                .stylesheets(StylesheetLocator.getStylesheet(Stylesheets.ALERT))
                .build();
    }

    private UserRegistration newUserRegistration() {
        return new UserRegistration(emailField.getText(),
                nameField.getText(),
                passwordField.getText(),
                confirmPasswordField.getText());
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
}

