package com.amalstack.notebooksfx.editor.command.text;

public class ImageTextEditorCommand implements TextEditorCommand {

    private String url = "https://picsum.photos/200";

    public ImageTextEditorCommand() {

    }

    public ImageTextEditorCommand(String url) {
        this.url = url;
    }

    @Override
    public CharSequence execute(CharSequence text) {
        return "![" + text + "]" + "(" + url + ")";
    }
}

