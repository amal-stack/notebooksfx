package com.amalstack.notebooksfx.editor.command;

public class UrlTextEditorCommand implements TextEditorCommand {

    private String url = "https://example.com";

    public UrlTextEditorCommand() {

    }

    public UrlTextEditorCommand(String url) {
        this.url = url;
    }

    @Override
    public CharSequence execute(CharSequence text) {
        return "[" + text + "]" + "(" + url + ")";
    }
}


