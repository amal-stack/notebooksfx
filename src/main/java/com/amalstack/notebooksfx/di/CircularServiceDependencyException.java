package com.amalstack.notebooksfx.di;

import java.text.MessageFormat;

public final class CircularServiceDependencyException extends RuntimeException {
    private final static String ERR_MSG_FORMAT = "Circular dependency between service \"{0}\" and service \"{1}\"";

    private final Class<?> service1;

    private final Class<?> service2;


    public CircularServiceDependencyException(Class<?> service1, Class<?> service2) {
        this(service1, service2, null);
    }

    public CircularServiceDependencyException(Class<?> service1, Class<?> service2, Throwable cause) {
        super(MessageFormat.format(ERR_MSG_FORMAT, service1.getName(), service2.getName()), cause);
        this.service1 = service1;
        this.service2 = service2;
    }

    public CircularServiceDependencyException(String message) {
        super(message);
        service1 = service2 = null;
    }

    public Class<?> getService1() {
        return service1;
    }

    public Class<?> getService2() {
        return service2;
    }
}
