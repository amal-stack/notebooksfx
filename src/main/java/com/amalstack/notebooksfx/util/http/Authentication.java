package com.amalstack.notebooksfx.util.http;

import java.util.Objects;
import java.util.Optional;

public final class Authentication<U> {
    public static final Authentication<?> ANONYMOUS = new Authentication<>(false, null, null);
    private final boolean isAuthenticated;
    private final U user;
    private final String authorizationHeader;

    private Authentication(
            boolean isAuthenticated,
            U user,
            String authorizationHeader) {
        this.isAuthenticated = isAuthenticated;
        this.user = user;
        this.authorizationHeader = authorizationHeader;
    }

    static <U> Authentication<U> forUser(U user, String authorizationHeader) {
        return new Authentication<>(true, user, authorizationHeader);
    }

    @Override
    public String toString() {
        return "Authentication[" +
                "user=" + user +
                ']';
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public Optional<U> getUser() {
        return Optional.ofNullable(user);
    }

    public Optional<String> getAuthorizationHeader() {
        return Optional.ofNullable(authorizationHeader);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Authentication<?>) obj;
        return this.isAuthenticated == that.isAuthenticated &&
                Objects.equals(this.user, that.user) &&
                Objects.equals(this.authorizationHeader, that.authorizationHeader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAuthenticated, user, authorizationHeader);
    }

}


