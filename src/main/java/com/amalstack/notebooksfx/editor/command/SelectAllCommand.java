package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.editor.EditorContext;

public class SelectAllCommand implements Command {
    private final EditorContext context;

    public SelectAllCommand(EditorContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        context.getEditorControlProvider().getEditorTextArea().selectAll();
    }
}
