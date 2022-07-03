package com.amalstack.notebooksfx.editor;

import com.amalstack.notebooksfx.editor.command.CommandCode;
import javafx.scene.Node;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.util.Collection;

public interface EditorControlProvider {
    Node getControl(CommandCode op);

    Collection<Node> getAllControls();

    StyleClassedTextArea getEditorTextArea();
}
