package com.amalstack.notebooksfx.editor.command;

public class OrderedListTextEditorCommand implements TextEditorCommand {

    protected final String TEXT = """
            1. Item 1
            2. Item 2
            3. Item 3
            """;

    @Override
    public CharSequence execute(CharSequence text) {
        return TEXT;
    }
}
