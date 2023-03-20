package com.amalstack.notebooksfx.editor.command;

public class HorizontalRuleTextEditorCommand implements TextEditorCommand {

    private int lineCharLength;

    private char lineChar = '-';

    public HorizontalRuleTextEditorCommand(int lineCharLength) {

        this.lineCharLength = lineCharLength;
    }

    public HorizontalRuleTextEditorCommand() {
        this.lineCharLength = 25;
    }

    public int getLineCharLength() {
        return lineCharLength;
    }

    public void setLineCharLength(int lineCharLength) {
        this.lineCharLength = lineCharLength;
    }

    public char getLineChar() {
        return lineChar;
    }

    public void setLineChar(char lineChar) {
        this.lineChar = lineChar;
    }

    @Override
    public CharSequence execute(CharSequence text) {
        String lineSeparator = System.lineSeparator();
        return lineSeparator + String.valueOf(lineChar).repeat(lineCharLength) + lineSeparator;
    }
}

