package com.amalstack.notebooksfx.editor.command.text;

import com.amalstack.notebooksfx.command.ParameterizedResultCommand;

@FunctionalInterface
public interface TextEditorCommand extends ParameterizedResultCommand<CharSequence, CharSequence> {
}
