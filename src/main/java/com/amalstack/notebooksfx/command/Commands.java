package com.amalstack.notebooksfx.command;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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

    public static EventHandler<ActionEvent> actionEventHandler(Command command) {
        return event -> command.execute();
    }

}

