package com.amalstack.notebooksfx.command;

@FunctionalInterface
public interface ParameterizedCommand<T> {
    void execute(T arg);
}
