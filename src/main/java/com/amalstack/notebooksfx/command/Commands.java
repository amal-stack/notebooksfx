package com.amalstack.notebooksfx.command;

import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.function.Function;

public class Commands {

    private Commands() {
    }

    public static Command empty() {
        return () -> {
        };
    }


    public static Command sequence(Command... commands) {
        return () -> {
            for (Command command : commands) {
                command.execute();
            }
        };
    }

    public static <T extends Event> EventHandler<T> eventHandler(Command command) {
        return event -> command.execute();
    }

    public static <T extends Event, A> EventHandler<T> eventHandlerWithEventArg(ParameterizedCommand<A> command, Function<T, A> argProvider) {
        return event -> command.execute(argProvider.apply(event));
    }

}

