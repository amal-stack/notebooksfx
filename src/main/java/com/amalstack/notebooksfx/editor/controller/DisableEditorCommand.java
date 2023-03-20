package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.command.Command;
import org.fxmisc.richtext.StyleClassedTextArea;

/**
 * Disables the editor and sets the editor text to a placeholder message.
 *
 * @author Amal Krishna
 */
class DisableEditorCommand implements Command {

    private final StyleClassedTextArea editorTextArea;
    String PLACEHOLDER_TEXT = "Select a page to edit";

    DisableEditorCommand(StyleClassedTextArea editorTextArea) {
        this.editorTextArea = editorTextArea;
    }

    @Override
    public void execute() {
        editorTextArea.setEditable(false);
        editorTextArea.replaceText(PLACEHOLDER_TEXT);
    }
}
