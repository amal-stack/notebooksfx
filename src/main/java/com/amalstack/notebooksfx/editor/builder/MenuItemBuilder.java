package com.amalstack.notebooksfx.editor.builder;

import javafx.scene.control.MenuItem;
import org.controlsfx.glyphfont.Glyph;

public class MenuItemBuilder extends EditorControlBuilder<MenuItem, MenuItemBuilder> {

    @Override
    protected MenuItemBuilder self() {
        return this;
    }

    @Override
    public MenuItem build() {
        validateState();
        var menuItem = new MenuItem(text);
        menuItem.setId(id);
        menuItem.setOnAction(this::handleEditorCommand);
        if (glyph != null) {
            menuItem.setGraphic(new Glyph("FontAwesome", glyph));
        }
        return menuItem;
    }
}
