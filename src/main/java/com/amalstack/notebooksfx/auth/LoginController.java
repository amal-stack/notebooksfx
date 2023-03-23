package com.amalstack.notebooksfx.auth;

import com.amalstack.notebooksfx.data.model.ErrorEntry;
import com.amalstack.notebooksfx.data.model.User;
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

    private final ObservableList<ErrorEntry> loginErrorEntries = FXCollections.observableArrayList();

    @FXML
    private ScrollPane loginErrorScrollPane;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    public LoginController(AuthenticationService authenticationService,
                           NavigationManager navigationManager) {
        this.authenticationService = authenticationService;
        this.navigationManager = navigationManager;
    }

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> login());
        loginErrorScrollPane.setContent(
                ErrorTableViewFactory.create(loginErrorEntries, "loginView"));
    }

    @FXML
    public void login() {
        loginErrorScrollPane.setVisible(false);
        loginErrorEntries.clear();
        String email = emailField.getText();
        char[] password = passwordField.getText().toCharArray();
        var result = authenticationService.authenticate(email, password, User.class);
        if (result.isSuccess()) {
            navigationManager.navigateTo(Parents.HOME);
            return;
        }
        result.getError().ifPresent(error -> {

            if (error.status() == 401) {
                loginErrorEntries.add(new ErrorEntry("Login Failed", "Invalid credentials"));
            }
            loginErrorEntries.addAll(ErrorEntry.fromError(error));
            loginErrorScrollPane.setVisible(true);
        });
    }
}