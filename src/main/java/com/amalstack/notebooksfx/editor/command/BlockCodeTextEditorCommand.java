package com.amalstack.notebooksfx.editor.command;


public class BlockCodeTextEditorCommand extends EncloseTextEditorCommand {

    private final String AFFIX = "\n```\n";

    @Override
    protected CharSequence getPrefix() {
        return AFFIX;
    }

    @Override
    protected CharSequence getSuffix() {
        return AFFIX;
    }
}
