package com.amalstack.notebooksfx.editor.command;

@FunctionalInterface
public interface TextEditorCommand {
    CharSequence execute(CharSequence text);
}
