package com.amalstack.notebooksfx.editor;

import org.fxmisc.richtext.StyleClassedTextArea;

public interface EditorContextFactory {
    EditorContext create(StyleClassedTextArea textArea);
}

