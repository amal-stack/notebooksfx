package com.amalstack.notebooksfx.di;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public record ServiceDescriptor<A, I extends A>(
        @NotNull Class<A> abstractionType,
        @NotNull Class<I> implementationType,
        @NotNull Lifetime lifetime,

        Supplier<I> factory,

        Consumer<A> postConstructConfig) {

    public ServiceDescriptor(
        @NotNull Class<A> abstractionType,
        @NotNull Class<I> implementationType,
        @NotNull Lifetime lifetime) {
        this(abstractionType, implementationType, lifetime, null, null);
    }

    public ServiceDescriptor(
        @NotNull Class<A> abstractionType,
        @NotNull Class<I> implementationType,
        @NotNull Lifetime lifetime,
        Supplier<I> factory) {
        this(abstractionType, implementationType, lifetime, factory, null);
    }

    public ServiceDescriptor(
        @NotNull Class<A> abstractionType,
        @NotNull Class<I> implementationType,
        @NotNull Lifetime lifetime,
        Consumer<A> postConstructConfig) {
        this(abstractionType, implementationType, lifetime, null, postConstructConfig);
    }
}
