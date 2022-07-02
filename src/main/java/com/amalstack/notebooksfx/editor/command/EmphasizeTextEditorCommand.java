package com.amalstack.notebooksfx.editor.command;

public class EmphasizeTextEditorCommand extends EncloseTextEditorCommand {
    private static final String AFFIX = "*";
    @Override
    public CharSequence getPrefix() {
        return AFFIX;
    }

    @Override
    public CharSequence getSuffix() {
        return AFFIX;
    }
}
