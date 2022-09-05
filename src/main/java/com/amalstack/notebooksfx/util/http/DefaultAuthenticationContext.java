package com.amalstack.notebooksfx.util.http;

public class DefaultAuthenticationContext implements AuthenticationContext {
    private Authentication authentication;

    @Override
    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public void clearAuthentication() {
        this.authentication = null;
    }

    @Override
    public boolean isAuthenticated() {
        return authentication != null;
    }
}
