package com.amalstack.notebooksfx.util.http;

public class DefaultAuthenticationContext implements AuthenticationContext {
    private Authentication<?> authentication;

    @SuppressWarnings("unchecked")
    @Override
    public <U> Authentication<U> getAuthentication() {
        return (Authentication<U>) authentication;
    }

    @Override
    public <U> void setAuthentication(Authentication<U> authentication) {
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
