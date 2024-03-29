package com.amalstack.notebooksfx.util;

@FunctionalInterface
public interface Initializer {
    static Initializer empty() {
        return () -> {
        };
    }

    static Initializer combine(Initializer... initializers) {
        return () -> {
            for (Initializer initializer : initializers) {
                initializer.initialize();
            }
        };
    }

    static void initialize(Initializer initializer) {
        initializer.initialize();
    }

    static void initialize(Initializer... initializers) {
        for (Initializer initializer : initializers) {
            initializer.initialize();
        }
    }

    void initialize();

}

