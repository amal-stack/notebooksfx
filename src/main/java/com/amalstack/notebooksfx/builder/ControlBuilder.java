package com.amalstack.notebooksfx.builder;

import java.util.function.Consumer;

public interface ControlBuilder<C, B extends ControlBuilder<C, B>> {
    B withId(String id);

    B configure(Consumer<C> config);
}
