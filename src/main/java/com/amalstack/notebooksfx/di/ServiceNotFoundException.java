package com.amalstack.notebooksfx.di;

import java.text.MessageFormat;

public final class ServiceNotFoundException extends RuntimeException {
    private final Class<?> service;
    private final static String ERR_MSG_FORMAT = "Service \"{0}\" could not be resolved by the container";

    public ServiceNotFoundException(Class<?> service) {
        super(MessageFormat.format(ERR_MSG_FORMAT, service.getName()));
        this.service = service;
    }

    public ServiceNotFoundException(Class<?> service, Throwable cause) {
        super(MessageFormat.format(ERR_MSG_FORMAT, service.getName()), cause);
        this.service = service;
    }

    public Class<?> getService() {
        return service;
    }
}
