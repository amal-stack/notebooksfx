package com.amalstack.notebooksfx.editor.builder;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import org.controlsfx.glyphfont.Glyph;

public class EditorButtonBuilder extends EditorControlBuilder<Button, EditorButtonBuilder> {

    public static EditorButtonBuilder create() {
        return new EditorButtonBuilder();
    }

    @Override
    protected EditorButtonBuilder self() {
        return this;
    }

    @Override
    public Button build() {
        validateState();
        var button = new Button(text);
        button.setId(id);
        button.setOnAction(this::handleEditorCommand);
        if (glyph != null) {
            button.setGraphic(new Glyph("FontAwesome", glyph));
        }
        button.setTooltip(new Tooltip(command.getClass().getSimpleName().replace("TextEditorCommand", "")));
        return button;
    }
}


