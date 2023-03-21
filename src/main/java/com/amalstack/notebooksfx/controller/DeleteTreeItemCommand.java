package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.command.Command;
import javafx.scene.control.TreeItem;

class DeleteTreeItemCommand implements Command {
    private final NotebookTreeViewContext context;
    private final TreeItem<TreeItemModel> treeItem;

    public DeleteTreeItemCommand(NotebookTreeViewContext context, TreeItem<TreeItemModel> treeItem) {
        this.context = context;
        this.treeItem = treeItem;
    }

    @Override
    public void execute() {
        if (treeItem == null) {
            return;
        }
        if (treeItem.getValue() instanceof NotebookTreeItemModel) {
            throw new IllegalArgumentException("Cannot delete root notebook tree item.");
        }
        if (treeItem.getValue() instanceof SectionTreeItemModel) {
            Alerts.showConfirmationAlert("Delete Section",
                    "Are you sure you want to delete the section \"" + treeItem.getValue().getName() + "\"?",
                    this::deleteSection);
        }
        if (treeItem.getValue() instanceof PageTreeItemModel) {
            Alerts.showConfirmationAlert("Delete Page",
                    "Are you sure you want to delete the page \"" + treeItem.getValue().getName() + "\"?",
                    this::deletePage);
        }

    }

    protected void deleteSection() {
        context.getDataAccessService().sections().delete(treeItem.getValue().getId());
        treeItem.getParent().getChildren().remove(treeItem);
    }

    protected void deletePage() {
        context.getDataAccessService().pages().delete(treeItem.getValue().getId());
        treeItem.getParent().getChildren().remove(treeItem);
    }
}
