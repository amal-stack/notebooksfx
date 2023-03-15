package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.command.CommandCode;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

class EditorToolbarInitializer {
    private final EditorContext context;
    private final ToolBar editorToolbar;

    public EditorToolbarInitializer(EditorContext context, ToolBar editorToolbar) {
        this.context = context;
        this.editorToolbar = editorToolbar;
    }

    public void addControls() {
        var items = editorToolbar.getItems();
        items.add(context.getControl(CommandCode.BOLD));
        items.add(context.getControl(CommandCode.EMPHASIZE));
        items.add(context.getControl(CommandCode.UNDERLINE));

        items.add(new Separator());

        items.add(context.getControl(CommandCode.STRIKETHROUGH));
        items.add(context.getControl(CommandCode.SUPERSCRIPT));
        items.add(context.getControl(CommandCode.SUBSCRIPT));

        items.add(new Separator());

        items.add(context.getControl(CommandCode.HEADING));
        items.add(context.getControl(CommandCode.CODE));

        items.add(new Separator());

        items.add(context.getControl(CommandCode.UNORDERED_LIST));
        items.add(context.getControl(CommandCode.ORDERED_LIST));

        items.add(new Separator());

        items.add(context.getControl(CommandCode.URL));
        items.add(context.getControl(CommandCode.IMAGE));
        items.add(context.getControl(CommandCode.TABLE));

        items.add(new Separator());

        items.add(context.getControl(CommandCode.WRAP));
    }
}
