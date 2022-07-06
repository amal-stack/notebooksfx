package com.amalstack.notebooksfx;

@FunctionalInterface
public interface Command<T, R> {
    R execute(T arg);
}

