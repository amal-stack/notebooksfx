package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.editor.EditorContext;

public class UndoCommand implements Command {
    private final EditorContext editorContext;

    public UndoCommand(EditorContext editorContext) {
        this.editorContext = editorContext;
    }

    @Override
    public void execute() {
        editorContext.getEditorControlProvider().getEditorTextArea().undo();
    }
}
