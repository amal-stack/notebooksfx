package com.amalstack.notebooksfx.editor.command;

public class SubscriptTextEditorCommand extends EncloseTextEditorCommand {

    private final String AFFIX = "~";

    @Override
    protected CharSequence getPrefix() {
        return AFFIX;
    }

    @Override
    protected CharSequence getSuffix() {
        return AFFIX;
    }
}
