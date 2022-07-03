package com.amalstack.notebooksfx.editor.command;

public class HorizontalRuleTextEditorCommand implements TextEditorCommand {
    public int getLineCharLength() {
        return lineCharLength;
    }

    public void setLineCharLength(int lineCharLength) {
        this.lineCharLength = lineCharLength;
    }

    private int lineCharLength;

    public char getLineChar() {
        return lineChar;
    }

    public void setLineChar(char lineChar) {
        this.lineChar = lineChar;
    }

    private char lineChar = '-';

    public HorizontalRuleTextEditorCommand(int lineCharLength) {

        this.lineCharLength = lineCharLength;
    }

    public HorizontalRuleTextEditorCommand() {
        this.lineCharLength = 25;
    }

    @Override
    public CharSequence execute(CharSequence text) {
        String lineSeparator = System.lineSeparator();
        return lineSeparator + String.valueOf(lineChar).repeat(lineCharLength) + lineSeparator;
    }
}

