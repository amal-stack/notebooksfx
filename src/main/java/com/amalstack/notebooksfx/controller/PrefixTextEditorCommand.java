package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.editor.command.TextEditorCommand;

public abstract class PrefixTextEditorCommand implements TextEditorCommand {
    @Override
    public CharSequence execute(CharSequence text) {
        return null;
    }
}
