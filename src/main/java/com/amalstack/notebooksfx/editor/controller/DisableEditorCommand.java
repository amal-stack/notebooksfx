package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.command.Command;
import org.fxmisc.richtext.StyleClassedTextArea;

class DisableEditorCommand implements Command {

    private final StyleClassedTextArea editorTextArea;

    DisableEditorCommand(StyleClassedTextArea editorTextArea) {
        this.editorTextArea = editorTextArea;
    }

    @Override
    public void execute() {
        editorTextArea.setEditable(false);
        editorTextArea.replaceText("Select a page to edit");
    }
}
