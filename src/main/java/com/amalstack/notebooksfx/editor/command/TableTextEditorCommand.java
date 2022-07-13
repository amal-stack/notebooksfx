package com.amalstack.notebooksfx.editor.command;

public class TableTextEditorCommand implements TextEditorCommand {

    protected final String TEXT = """
         
                        
            | Column 1 | Column 2 | Column 3 |
            |----------|----------|----------|
            | Row 1.1  | Row 1.2  | Row 1.3  |
            | Row 2.1  | Row 2.2  | Row 2.3  |
            """;

    @Override
    public CharSequence execute(CharSequence text) {
        return TEXT;
    }
}

