package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.Command;

@FunctionalInterface
public interface TextEditorCommand extends Command<CharSequence, CharSequence> {
}
