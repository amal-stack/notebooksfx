package com.amalstack.notebooksfx.editor.command.text;

public abstract class EncloseTextEditorCommand implements TextEditorCommand {
    @Override
    public CharSequence execute(CharSequence text) {
        return getPrefix().toString() + text + getSuffix().toString();
    }

    protected abstract CharSequence getPrefix();

    protected abstract CharSequence getSuffix();
}

