package com.amalstack.notebooksfx.command;

@FunctionalInterface
public interface ParameterizedResultCommand<T, R> {
    R execute(T arg);
}












