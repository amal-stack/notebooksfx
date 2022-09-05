package com.amalstack.notebooksfx.data.model;

public final class UserRegistration {
    private final String name;
    private final String email;
    private final String password;
    private final String confirmPassword;

    public UserRegistration(String email, String name, String password, String confirmPassword) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}



