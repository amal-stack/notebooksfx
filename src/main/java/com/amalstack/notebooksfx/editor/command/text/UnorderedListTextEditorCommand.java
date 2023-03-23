package com.amalstack.notebooksfx.editor.command.text;

public class UnorderedListTextEditorCommand implements TextEditorCommand {
    protected final String TEXT = """
            * Item 1
            * Item 2
            * Item 3
            """;

    @Override
    public CharSequence execute(CharSequence text) {
        return TEXT;
    }
}

