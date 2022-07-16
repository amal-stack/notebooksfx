package com.amalstack.notebooksfx.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public final class Container {
    private final Map<Class<?>, Class<?>> services = new HashMap<>();


    public <T> void addService(Class<T> abstraction, Class<? extends T> implementation) {
        validateAbstraction(abstraction);
        validateService(implementation);
        services.put(abstraction, implementation);
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> abstraction) {
        Class<? extends T> implType = (Class<? extends T>) services.get(abstraction);
        if (implType == null) {
            throw new RuntimeException("Service not found");
        }
        Constructor<? extends T> publicConstructor = (Constructor<? extends T>) implType.getConstructors()[0];
        return newInstanceOf(publicConstructor);
    }

    @SuppressWarnings("unchecked")
    public <T> T injectAndConstruct(Class<T> clazz) {
        validateService(clazz);
        Constructor<? extends T> publicConstructor = (Constructor<? extends T>) clazz.getConstructors()[0];
        return newInstanceOf(publicConstructor);
    }

    private <T> T newInstanceOf(Constructor<T> constructor) {
        var parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = getService(parameterTypes[i]);
        }

        try {
            return constructor.newInstance(parameters);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }


    private void validateService(Class<?> implementation) {
        if (implementation.getConstructors().length != 1) {
            throw new IllegalArgumentException("The service implementation must have exactly one public constructor");
        }
    }

    private void validateAbstraction(Class<?> abstraction) {
        if (!Modifier.isAbstract(abstraction.getModifiers()) && !abstraction.isInterface()) {
            throw new IllegalArgumentException("The parameter \"abstraction\" must represent an abstract class or an interface");
        }
    }
}
