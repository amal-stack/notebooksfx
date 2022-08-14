package com.amalstack.notebooksfx.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public final class Container {

    private final Map<Class<?>, ServiceDescriptor<?, ?>> services = new HashMap<>();
    private final Map<Class<?>, Object> singletonInstances = new HashMap<>();


    public <T> void addService(Class<T> abstraction,
                               Class<? extends T> implementation,
                               Lifetime lifetime) {
        validateAbstraction(abstraction);
        validateService(implementation);
        services.put(abstraction, new ServiceDescriptor<>(abstraction, implementation, lifetime));
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> abstraction) {
        var descriptor = (ServiceDescriptor<T, ? extends T>) services.get(abstraction);
        if (descriptor == null) {
            throw new ServiceNotFoundException(abstraction);
        }
        Class<? extends T> implType = descriptor.implementationType();

        Constructor<? extends T> publicConstructor = (Constructor<? extends T>) implType.getConstructors()[0];

        return switch (descriptor.lifetime()) {
            case TRANSIENT -> newInstanceOf(publicConstructor);
            case SINGLETON -> {
                T instance = (T) singletonInstances.getOrDefault(abstraction, null);
                if (instance == null) {
                    instance = newInstanceOf(publicConstructor);
                    singletonInstances.put(abstraction, instance);
                }
                yield instance;
            }
        };
    }


    @SuppressWarnings("unchecked")
    public <T> T injectAndConstruct(Class<T> clazz) {
        validateService(clazz);
        Constructor<? extends T> publicConstructor = (Constructor<? extends T>) clazz.getConstructors()[0];
        return newInstanceOf(publicConstructor);
    }

    public Lifetime getServiceLifetime(Class<?> abstraction) {
        var descriptor = services.get(abstraction);
        if (descriptor == null) {
            throw new ServiceNotFoundException(abstraction);
        }
        return descriptor.lifetime();
    }

    public <T> void addSingleton(Class<T> abstraction,
                                 Class<? extends T> implementation) {
        addService(abstraction, implementation, Lifetime.SINGLETON);
    }

    public <T> void addTransient(Class<T> abstraction,
                                 Class<? extends T> implementation) {
        addService(abstraction, implementation, Lifetime.TRANSIENT);
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
            throw new IllegalArgumentException("The service implementation " + implementation.getName() + " must have exactly one public constructor");
        }
    }

    private void validateAbstraction(Class<?> abstraction) {
        if (!Modifier.isAbstract(abstraction.getModifiers()) && !abstraction.isInterface()) {
            throw new IllegalArgumentException("The abstraction " + abstraction.getName() + "  must represent an abstract class or an interface");
        }
    }
}

