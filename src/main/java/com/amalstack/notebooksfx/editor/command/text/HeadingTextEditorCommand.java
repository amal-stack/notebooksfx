package com.amalstack.notebooksfx.editor.command.text;

public class HeadingTextEditorCommand implements TextEditorCommand {
    public final String PREFIX = "#";
    private final int level;

    public HeadingTextEditorCommand(int level) {
        if (level < 1) {
            level = 1;
        }
        if (level > 6) {
            level = 6;
        }
        this.level = level;
    }

    @Override
    public CharSequence execute(CharSequence text) {
        var lineSeparator = System.lineSeparator();
        return lineSeparator + PREFIX.repeat(level) + " " + text + lineSeparator;
    }
}
