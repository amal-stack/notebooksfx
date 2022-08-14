package com.amalstack.notebooksfx.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class Container {

    private final Map<Class<?>, ServiceDescriptor<?, ?>> services = new HashMap<>();
    private final Map<Class<?>, Object> singletonInstances = new HashMap<>();


    public <A, I extends A> void addService(Class<A> abstraction,
                               Class<I> implementation,
                               Lifetime lifetime) {
        addService(abstraction, implementation, lifetime, null, null);
    }

    public <A, I extends A> void addService(Class<A> abstraction,
                               Class<I> implementation,
                               Lifetime lifetime,
                               Supplier<I> implementationFactory) {
        addService(abstraction, implementation, lifetime, implementationFactory, null);
    }

    public <A, I extends A> void addService(Class<A> abstraction,
                               Class<I> implementation,
                               Lifetime lifetime,
                               Consumer<A> postConstructConfig) {
        addService(abstraction, implementation, lifetime, null, postConstructConfig);
    }

    public <A, I extends A> void addService(Class<A> abstraction,
                                            Class<I> implementation,
                                            Lifetime lifetime,
                                            Supplier<I> implementationFactory,
                                            Consumer<A> postConstructConfig) {
        validateAbstraction(abstraction);
        validateService(implementation);

        services.put(abstraction, new ServiceDescriptor<>(
                abstraction,
                implementation,
                lifetime,
                implementationFactory,
                postConstructConfig));
    }

    @SuppressWarnings("unchecked")
    public <T, I extends T> T getService(Class<T> abstraction) {
        var descriptor = (ServiceDescriptor<T, I>) services.get(abstraction);
        if (descriptor == null) {
            throw new ServiceNotFoundException(abstraction);
        }
        Class<I> implType = descriptor.implementationType();

        Constructor<I> publicConstructor = (Constructor<I>) implType.getConstructors()[0];

        return switch (descriptor.lifetime()) {
            case SINGLETON -> {
                T instance = (T) singletonInstances.getOrDefault(abstraction, null);
                if (instance == null) {
                    instance = newInstanceOf(descriptor, publicConstructor);
                    singletonInstances.put(abstraction, instance);
                }
                yield instance;
            }
            case TRANSIENT -> newInstanceOf(descriptor, publicConstructor);
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

    private <A, I extends A> A newInstanceOf(ServiceDescriptor<A, I> descriptor, Constructor<I> publicConstructor) {
        I instance;
        Supplier<I> factory = descriptor.factory();
        Consumer<A> postConstructConfig = descriptor.postConstructConfig();
        instance = factory != null ? factory.get() : newInstanceOf(publicConstructor);
        if (postConstructConfig != null) {
            postConstructConfig.accept(instance);
        }
        return instance;
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

