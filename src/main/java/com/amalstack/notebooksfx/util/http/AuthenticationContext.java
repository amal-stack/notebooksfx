package com.amalstack.notebooksfx.util.http;

public interface AuthenticationContext {
    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);

    void clearAuthentication();

    boolean isAuthenticated();

}

