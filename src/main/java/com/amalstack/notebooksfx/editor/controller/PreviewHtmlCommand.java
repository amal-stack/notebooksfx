package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.editor.EditorContext;
import javafx.scene.web.WebView;

class PreviewHtmlCommand implements Command {

    private final EditorContext editorContext;
    private final WebView outputWebView;

    public PreviewHtmlCommand(EditorContext editorContext, WebView outputWebView) {
        this.editorContext = editorContext;
        this.outputWebView = outputWebView;
    }

    @Override
    public void execute() {
        String html = editorContext.toHtml();
        System.out.println(html);
        outputWebView.getEngine().loadContent(html, "text/html");
    }
}
