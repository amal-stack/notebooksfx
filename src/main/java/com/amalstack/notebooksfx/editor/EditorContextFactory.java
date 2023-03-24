package com.amalstack.notebooksfx.editor;

import javafx.scene.web.WebView;
import org.fxmisc.richtext.StyleClassedTextArea;

public interface EditorContextFactory {
    EditorContext create(StyleClassedTextArea textArea, WebView outputWebView);
}

