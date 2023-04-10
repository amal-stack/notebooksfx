package com.amalstack.notebooksfx.util;

import javafx.concurrent.Task;

import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Tasks {
    public static <T> TaskBuilder<T> wrap(Supplier<T> task) {
        return new TaskBuilder<>(task);
    }

    public static void execute(Task<?> task) {
        var executor = Executors.newSingleThreadExecutor();
        executor.submit(task);
        executor.shutdown();
    }

    public static class TaskBuilder<T> {
        private final Supplier<T> task;
        private Consumer<T> successAction;
        private Consumer<Throwable> exceptionAction;

        private TaskBuilder(Supplier<T> task) {
            this.task = task;
        }

        public TaskBuilder<T> onSucceeded(Consumer<T> successAction) {
            this.successAction = successAction;
            return this;
        }

        public TaskBuilder<T> onFailed(Consumer<Throwable> exceptionAction) {
            this.exceptionAction = exceptionAction;
            return this;
        }

        public TaskBuilder<T> throwOnFailed() {
            this.exceptionAction = throwable -> {
                throw new RuntimeException(throwable);
            };
            return this;
        }

        public Task<T> build() {
            return new Task<>() {
                @Override
                protected T call() {
                    return task.get();
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    if (successAction != null) {
                        successAction.accept(getValue());
                    }
                }

                @Override
                protected void failed() {
                    super.failed();
                    if (exceptionAction != null) {
                        exceptionAction.accept(getException());
                    }
                }
            };
        }
    }
}



