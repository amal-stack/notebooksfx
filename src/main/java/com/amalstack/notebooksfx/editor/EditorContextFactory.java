package com.amalstack.notebooksfx.editor;

import javafx.scene.web.WebView;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.ResourceBundle;

public interface EditorContextFactory {
    EditorContext create(StyleClassedTextArea textArea, WebView outputWebView, ResourceBundle resources);
}

