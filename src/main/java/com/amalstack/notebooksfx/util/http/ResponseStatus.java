package com.amalstack.notebooksfx.util.http;

public enum ResponseStatus {
    UNKNOWN_ERROR,
    INFORMATIONAL,
    SUCCESS,
    REDIRECTION,
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    CLIENT_ERROR,
    SERVER_ERROR;


    public static ResponseStatus of(int statusCode) {
        if (statusCode > 599) {
            return UNKNOWN_ERROR;
        }
        if (statusCode > 499) {
            return SERVER_ERROR;
        }
        if (statusCode > 399) {
            return switch (statusCode) {
                case 400 -> BAD_REQUEST;
                case 401 -> UNAUTHORIZED;
                case 403 -> FORBIDDEN;
                case 404 -> NOT_FOUND;
                default -> CLIENT_ERROR;
            };
        }
        if (statusCode > 299) {
            return REDIRECTION;
        }
        if (statusCode > 199) {
            return SUCCESS;
        }
        if (statusCode > 99) {
            return INFORMATIONAL;
        }
        return UNKNOWN_ERROR;
    }

    public String label() {
        return switch (this) {
            case UNKNOWN_ERROR -> "Unknown Error";
            case INFORMATIONAL -> "Informational";
            case SUCCESS -> "Success";
            case REDIRECTION -> "Redirection";
            case BAD_REQUEST -> "Bad Request";
            case UNAUTHORIZED -> "Unauthorized";
            case FORBIDDEN -> "Forbidden";
            case NOT_FOUND -> "Not Found";
            case CLIENT_ERROR -> "Client Error";
            case SERVER_ERROR -> "Server Error";
        };
    }
}
