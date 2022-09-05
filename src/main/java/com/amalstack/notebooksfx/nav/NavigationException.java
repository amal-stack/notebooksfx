package com.amalstack.notebooksfx.nav;

public class NavigationException extends RuntimeException {
    public NavigationException(String message) {
        super(message);
    }

    public NavigationException(Throwable cause) {
        super(cause);
    }

    public NavigationException(String message, Throwable cause) {
        super(message, cause);
    }
}
