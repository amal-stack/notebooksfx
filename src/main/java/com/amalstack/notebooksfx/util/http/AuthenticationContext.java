package com.amalstack.notebooksfx.util.http;

public interface AuthenticationContext {
    <U> Authentication<U> getAuthentication();

    <U> void setAuthentication(Authentication<U> authentication);

    void clearAuthentication();

    boolean isAuthenticated();
}

