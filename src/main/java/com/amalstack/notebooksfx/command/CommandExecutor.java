package com.amalstack.notebooksfx.command;

public class CommandExecutor {
    public static void execute(Command command) {
        command.execute();
    }

    public static void execute(Command... commands) {
        for (Command command : commands) {
            command.execute();
        }
    }

    public static <T> void execute(ParameterizedCommand<T> command, T arg) {
        command.execute(arg);
    }

    public static <R> R execute(ResultCommand<R> command) {
        return command.execute();
    }

    public static <T, R> R execute(ParameterizedResultCommand<T, R> command, T arg) {
        return command.execute(arg);
    }
}
