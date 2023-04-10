package com.amalstack.notebooksfx.auth;

import com.amalstack.notebooksfx.css.StylesheetLocator;
import com.amalstack.notebooksfx.css.Stylesheets;
import com.amalstack.notebooksfx.data.model.User;
import com.amalstack.notebooksfx.nav.NavigationManager;
import com.amalstack.notebooksfx.util.Tasks;
import com.amalstack.notebooksfx.util.controls.Alerts;
import com.amalstack.notebooksfx.util.http.AuthenticationService;
import com.amalstack.notebooksfx.util.http.ErrorResponse;
import com.amalstack.notebooksfx.views.ViewNames;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class LoginController {
    private final AuthenticationService authenticationService;
    private final NavigationManager navigationManager;
    private final BooleanProperty taskInProgress = new SimpleBooleanProperty(false);

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private ResourceBundle resources;

    public LoginController(AuthenticationService authenticationService,
                           NavigationManager navigationManager) {
        this.authenticationService = authenticationService;
        this.navigationManager = navigationManager;
    }

    public BooleanProperty taskInProgressProperty() {
        return taskInProgress;
    }

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> login());
    }

    @FXML
    public void login() {
        String email = emailField.getText();
        char[] password = passwordField.getText().toCharArray();

        var task = Tasks
                .wrap(() -> authenticationService.authenticate(email, password, User.class))
                .onSucceeded(this::processLoginResult)
                .throwOnFailed()
                .build();

        taskInProgress.bind(task.runningProperty());

        Tasks.execute(task);
    }

    private void processLoginResult(AuthenticationService.Result<User, ? extends ErrorResponse> result) {
        if (result.isSuccess()) {
            navigationManager.navigateTo(ViewNames.HOME);
            return;
        }
        result.getError()
                .map(this::newLoginErrorAlert)
                .ifPresent(Alert::showAndWait);
    }

    private Alert newLoginErrorAlert(ErrorResponse error) {
        return Alerts.builder()
                .type(Alert.AlertType.ERROR)
                .title(resources.getString("auth.login.error.message.title"))
                .header(resources.getString("auth.login.error.message"))
                .message(AuthenticationErrorResponse.getErrorMessage(error, resources))
                .expandableContent(AuthenticationErrorResponse.createErrorNode(error, resources))
                .stylesheets(StylesheetLocator.getStylesheet(Stylesheets.ALERT))
                .build();
    }
}