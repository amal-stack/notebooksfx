package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.editor.EditorContext;

public class CutCommand implements Command {
    private final EditorContext context;

    public CutCommand(EditorContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        context.getEditorControlProvider().getEditorTextArea().cut();
    }
}
