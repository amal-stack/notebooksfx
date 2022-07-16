package com.amalstack.notebooksfx.di;

public record ServiceDescriptor<A, I extends A>(
        Class<A> abstractionType,
        Class<I> implementationType,
        Lifetime lifetime) {
}
