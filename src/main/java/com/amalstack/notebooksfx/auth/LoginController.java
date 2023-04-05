package com.amalstack.notebooksfx.auth;

import com.amalstack.notebooksfx.ViewNames;
import com.amalstack.notebooksfx.css.StylesheetLocator;
import com.amalstack.notebooksfx.css.Stylesheets;
import com.amalstack.notebooksfx.data.model.User;
import com.amalstack.notebooksfx.nav.NavigationManager;
import com.amalstack.notebooksfx.util.controls.Alerts;
import com.amalstack.notebooksfx.util.http.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class LoginController {
    private final AuthenticationService authenticationService;

    private final NavigationManager navigationManager;

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


    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> login());
    }

    @FXML
    public void login() {
        String email = emailField.getText();
        char[] password = passwordField.getText().toCharArray();

        var result = authenticationService.authenticate(email, password, User.class);

        if (result.isSuccess()) {
            navigationManager.navigateTo(ViewNames.HOME);
            return;
        }

        result.getError().ifPresent(error -> Alerts.builder()
                .type(Alert.AlertType.ERROR)
                .title(resources.getString("auth.login.error.message.title"))
                .header(resources.getString("auth.login.error.message"))
                .message(AuthenticationErrorResponse.getErrorMessage(error, resources))
                .expandableContent(AuthenticationErrorResponse.createErrorNode(error, resources))
                .stylesheets(StylesheetLocator.getStylesheet(Stylesheets.ALERT))
                .build()
                .showAndWait());
    }
}