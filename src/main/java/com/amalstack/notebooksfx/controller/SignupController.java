package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.data.model.ErrorEntry;
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
    @FXML
    private ScrollPane errorScrollPane;
    //    @FXML
//    private Label errorMessageLabel;
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
    private final ObservableList<ErrorEntry> errorEntries = FXCollections.observableArrayList();

    public SignupController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void initialize() {
        //errorMessageLabel.setWrapText(true);
        signUpButton.setOnAction(event -> signUp());
        errorScrollPane.setContent(ErrorTableViewFactory.create(errorEntries));
    }

    public void signUp() {

        errorScrollPane.setVisible(false);
        errorEntries.clear();
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        var result = authenticationService
                .registerUser(new UserRegistration(email, name, password, confirmPassword));

        if (result.isSuccess()) {
            return;
        }
        result.getError().ifPresent(error -> {
            errorEntries.addAll(ErrorEntry.fromError(error));
            errorScrollPane.setVisible(true);
        });
    }
}
