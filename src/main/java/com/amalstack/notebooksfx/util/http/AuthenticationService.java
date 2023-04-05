package com.amalstack.notebooksfx.util.http;

import java.util.Optional;

public interface AuthenticationService {

    <T, U> Result<U, ? extends ErrorResponse> registerUser(T user, Class<U> userType);

    <U> Result<U, ? extends ErrorResponse> authenticate(String username, char[] password, Class<U> userType);

    final class Result<U, E extends ErrorResponse> {
        private final boolean isSuccess;
        private final E error;
        private final U user;

        private Result(
                boolean isSuccess,
                U user,
                E error) {
            this.isSuccess = isSuccess;
            this.user = user;
            this.error = error;
        }

        public static <U, E extends ErrorResponse> Result<U, E> success(U user) {
            return new Result<>(true, user, null);
        }

        public static <U, E extends ErrorResponse> Result<U, E> success() {
            return new Result<>(true, null, null);
        }

        public static <U, E extends ErrorResponse> Result<U, E> failure(E error) {
            return new Result<>(false, null, error);
        }

        public Optional<E> getError() {
            return Optional.ofNullable(error);
        }

        public Optional<U> getUser() {
            return Optional.ofNullable(user);
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}
