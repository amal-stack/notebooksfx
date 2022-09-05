package com.amalstack.notebooksfx.util.http;

import com.amalstack.notebooksfx.data.model.ErrorResponse;
import com.amalstack.notebooksfx.data.model.User;
import com.amalstack.notebooksfx.data.model.UserRegistration;

import java.util.Optional;

public interface AuthenticationService {

    Result registerUser(UserRegistration user);

    Result authenticate(String username, char[] password);

    final class Result {
        private final boolean isSuccess;
        private final ErrorResponse error;
        private final User user;

        private Result(
                boolean isSuccess,
                User user,
                ErrorResponse error) {
            this.isSuccess = isSuccess;
            this.user = user;
            this.error = error;
        }

        public static Result success(User user) {
            return new Result(true, user, null);
        }

        public static Result success() {
            return new Result(true, null, null);
        }

        public static Result failure(ErrorResponse error) {
            return new Result(false, null, error);
        }

        public Optional<ErrorResponse> getError() {
            return Optional.ofNullable(error);
        }

        public Optional<User> getUser() {
            return Optional.ofNullable(user);
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}
