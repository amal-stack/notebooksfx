package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.editor.EditorContext;

public class PreviewHtmlCommand implements Command {

    private final EditorContext editorContext;

    public PreviewHtmlCommand(EditorContext editorContext) {
        this.editorContext = editorContext;
    }

    @Override
    public void execute() {
        String html = editorContext.toHtml();
        System.out.println(html);
        editorContext.getEditorControlProvider()
                .getOutputWebView()
                .getEngine()
                .loadContent(html, "text/html");
    }
}
