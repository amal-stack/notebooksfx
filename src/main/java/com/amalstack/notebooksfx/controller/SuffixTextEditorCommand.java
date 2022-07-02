package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.editor.command.TextEditorCommand;

public abstract class SuffixTextEditorCommand implements TextEditorCommand {
    @Override
    public CharSequence execute(CharSequence text) {
        return null;
    }
}
