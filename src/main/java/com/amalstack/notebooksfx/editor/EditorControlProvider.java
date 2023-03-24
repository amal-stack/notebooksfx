package com.amalstack.notebooksfx.editor;

import com.amalstack.notebooksfx.editor.command.text.CommandCode;
import javafx.scene.Node;
import javafx.scene.web.WebView;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.Collection;

public interface EditorControlProvider {
    Node getControl(CommandCode op);

    Collection<Node> getAllControls();

    StyleClassedTextArea getEditorTextArea();

    WebView getOutputWebView();
}
