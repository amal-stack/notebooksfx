package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.User;

import java.util.Objects;
import java.util.Optional;

public final class Authentication {
    public static final Authentication ANONYMOUS = new Authentication(false, null, null);
    private final boolean isAuthenticated;
    private final User user;
    private final String authorizationHeader;

    private Authentication(
            boolean isAuthenticated,
            User user,
            String authorizationHeader) {
        this.isAuthenticated = isAuthenticated;
        this.user = user;
        this.authorizationHeader = authorizationHeader;
    }

    static Authentication forUser(User user, String authorizationHeader) {
        return new Authentication(true, user, authorizationHeader);
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

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    public Optional<String> getAuthorizationHeader() {
        return Optional.ofNullable(authorizationHeader);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Authentication) obj;
        return this.isAuthenticated == that.isAuthenticated &&
                Objects.equals(this.user, that.user) &&
                Objects.equals(this.authorizationHeader, that.authorizationHeader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAuthenticated, user, authorizationHeader);
    }

}


