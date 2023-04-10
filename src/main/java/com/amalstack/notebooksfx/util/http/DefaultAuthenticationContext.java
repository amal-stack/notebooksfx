package com.amalstack.notebooksfx.util.http;

public class DefaultAuthenticationContext implements AuthenticationContext {
    private Authentication<?> authentication;

    @SuppressWarnings("unchecked")
    @Override
    public synchronized <U> Authentication<U> getAuthentication() {
        return (Authentication<U>) authentication;
    }

    @Override
    public synchronized <U> void setAuthentication(Authentication<U> authentication) {
        this.authentication = authentication;
    }

    @Override
    public synchronized void clearAuthentication() {
        this.authentication = null;
    }

    @Override
    public synchronized boolean isAuthenticated() {
        return authentication != null;
    }
}
