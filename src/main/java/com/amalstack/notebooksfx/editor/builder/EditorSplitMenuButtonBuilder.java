package com.amalstack.notebooksfx.editor.builder;

import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import org.controlsfx.glyphfont.Glyph;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class EditorSplitMenuButtonBuilder extends EditorControlBuilder<SplitMenuButton, EditorSplitMenuButtonBuilder> {
    protected final List<MenuItem> menuItems = new ArrayList<>();

    public static EditorSplitMenuButtonBuilder create() {
        return new EditorSplitMenuButtonBuilder();
    }

    public EditorSplitMenuButtonBuilder addMenuItem(Function<MenuItemBuilder, MenuItem> menuItemBuilder) {
        menuItems.add(menuItemBuilder.apply(new MenuItemBuilder().forEditor(textArea)));
        return this;
    }

    @Override
    protected EditorSplitMenuButtonBuilder self() {
        return this;
    }

    @Override
    public SplitMenuButton build() {
        validateState();
        var splitMenuButton = new SplitMenuButton();
        splitMenuButton.setId(id);
        splitMenuButton.setOnAction(this::handleEditorCommand);
        if (glyph != null) {
            splitMenuButton.setGraphic(new Glyph("FontAwesome", glyph));
        }
        splitMenuButton.getItems().addAll(menuItems);
        return splitMenuButton;
    }

    @Override
    protected void validateState() {
        try {
            super.validateState();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
