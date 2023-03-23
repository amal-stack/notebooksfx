package com.amalstack.notebooksfx.editor.nav.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.editor.nav.TreeItemModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class EditTreeItemCommand implements Command {

    private final TreeView<TreeItemModel> treeView;

    private final TreeItem<TreeItemModel> treeItem;

    public EditTreeItemCommand(TreeView<TreeItemModel> treeView, TreeItem<TreeItemModel> treeItem) {
        this.treeView = treeView;
        this.treeItem = treeItem;
    }

    @Override
    public void execute() {
        if (treeItem != null) {
            treeView.edit(treeItem);
        }
    }
}
