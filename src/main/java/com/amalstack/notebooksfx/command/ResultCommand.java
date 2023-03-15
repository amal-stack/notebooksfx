package com.amalstack.notebooksfx.command;

@FunctionalInterface
public interface ResultCommand<R> {
    R execute();
}
