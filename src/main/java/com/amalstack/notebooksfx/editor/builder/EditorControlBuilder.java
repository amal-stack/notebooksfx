package com.amalstack.notebooksfx.editor.builder;

import com.amalstack.notebooksfx.editor.command.TextEditorCommand;
import javafx.event.ActionEvent;
import org.controlsfx.glyphfont.FontAwesome;
import org.fxmisc.richtext.StyleClassedTextArea;

public abstract class EditorControlBuilder<C, B extends EditorControlBuilder<C, B>> {
    protected StyleClassedTextArea textArea;
    protected String id;
    protected String text = "";
    protected TextEditorCommand command;
    protected FontAwesome.Glyph glyph;

    protected abstract B self();

    public B forEditor(StyleClassedTextArea textArea) {
        this.textArea = textArea;
        return self();
    }

    public B withId(String id) {
        this.id = id;
        return self();
    }

    public B withText(String text) {
        this.text = text;
        return self();
    }

    public B withGlyph(FontAwesome.Glyph glyph) {
        this.glyph = glyph;
        return self();
    }

    public B performs(TextEditorCommand command) {
        this.command = command;
        return self();
    }

    public abstract C build();

    protected void handleEditorCommand(ActionEvent event) {
        var selection = textArea.getSelection();
        if (selection.getLength() == 0) {
            textArea.appendText(command.execute("Text Here").toString());
            return;
        }
        textArea.replaceText(selection, command.execute(textArea.getText(selection)).toString());
    }

    protected void validateState() {
        if (textArea == null) {
            throw new IllegalStateException("The editor for the editor control is not set.");
        }
        if (id == null) {
            throw new IllegalStateException("The id for the editor control is not set.");
        }
        if (command == null) {
            throw new IllegalStateException("The action performed for the editor control is not set.");
        }
    }
}
