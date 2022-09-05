package com.amalstack.notebooksfx.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Container {

    private final Map<Class<?>, ServiceDescriptor<?, ?>> services = new HashMap<>();
    private final Map<Class<?>, Object> singletonInstances = new HashMap<>();

    private final VisitChain visitChain = new VisitChain();

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
        validateImplementation(implementation, implementationFactory);

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
                } else System.out.println("Using singleton instance of " + abstraction.getName());
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

    private <A, I extends A> A newInstanceOf(ServiceDescriptor<A, I> descriptor,
                                             Constructor<I> publicConstructor) {
        I instance;
        Supplier<I> factory = descriptor.factory();
        Consumer<A> postConstructConfig = descriptor.postConstructConfig();
        if (factory != null) {
            instance = factory.get();
            System.out.println("Created instance of " + instance.getClass().getName() + " using factory");
        } else instance = newInstanceOf(publicConstructor);
        if (postConstructConfig != null) {
            postConstructConfig.accept(instance);
        }

        return instance;
    }

    private <T> T newInstanceOf(Constructor<T> constructor) {
        var parameterTypes = constructor.getParameterTypes();
        System.out.println("1. Requested instance of " + constructor.getDeclaringClass().getName());
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            var desc = services.get(parameterTypes[i]);
            if (desc != null && visitChain.requested(desc)) {
                throw new CircularServiceDependencyException(visitChain.toString());
            }
            System.out.println("2. Resolving parameter " + parameterTypes[i].getName());

            parameters[i] = getService(parameterTypes[i]);
            visitChain.resolved(parameterTypes[i]);
            System.out.println("3. Resolved parameter " + parameterTypes[i].getName());
        }
        System.out.println("4. Creating instance of " + constructor.getDeclaringClass().getName());
        System.out.println();
        try {
            return constructor.newInstance(parameters);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void validateService(Class<?> service) {
        if (service.getConstructors().length != 1) {
            throw new IllegalArgumentException("The service"
                    + service.getName()
                    + " must have exactly one public constructor.");
        }
    }

    private void validateImplementation(Class<?> implementation, Supplier<?> implementationFactory) {
        if (implementation.getConstructors().length != 1 && implementationFactory == null) {
            throw new IllegalArgumentException("The service implementation "
                    + implementation.getName()
                    + " must have exactly one public constructor."
                    + "Remove the additional constructors or provide an implementation factory to allow multiple constructors.");
        }
    }

    private void validateAbstraction(Class<?> abstraction) {
        if (!Modifier.isAbstract(abstraction.getModifiers()) && !abstraction.isInterface()) {
            throw new IllegalArgumentException("The abstraction "
                    + abstraction.getName()
                    + "  must represent an abstract class or an interface");
        }
    }

    private static final class VisitChain {
        public final Map<Class<?>, ServiceDescriptor<?, ?>> visited = new LinkedHashMap<>();

        public ServiceDescriptor<?, ?> lastRevisited;

        public boolean requested(ServiceDescriptor<?, ?> descriptor) {
            var abstraction = descriptor.abstractionType();
            if (visited.containsKey(abstraction)) {
                lastRevisited = descriptor;
                System.out.println("Already visited " + abstraction.getName());
                return true;
            }
            visited.put(abstraction, descriptor);
            return false;
        }

        public void resolved(Class<?> clazz) {
            visited.remove(clazz);
        }

        public void reset() {
            visited.clear();
        }


        @Override
        public String toString() {
            return visited.values().stream()
                    .map(serviceDescriptor -> serviceDescriptor.implementationType().getName())
                    .collect(Collectors.joining(" -> "))
                    + (lastRevisited != null ? " -> " + lastRevisited.implementationType().getName() : "");
        }
    }
}

