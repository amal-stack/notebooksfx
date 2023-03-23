package com.amalstack.notebooksfx.editor.nav;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.data.model.PageInput;
import javafx.scene.control.TreeItem;

class CreatePageCommand implements Command {

    private final TreeItem<TreeItemModel> treeItem;

    private final NotebookTreeViewContext context;

    public CreatePageCommand(TreeItem<TreeItemModel> treeItem,
                             NotebookTreeViewContext context) {
        this.treeItem = treeItem;
        this.context = context;
    }

    @Override
    public void execute() {
        if (treeItem.getValue() instanceof SectionTreeItemModel section) {
            var page = context.getDataAccessService()
                    .pages()
                    .create(new PageInput("New Page", "", section.getId()));

            PageTreeItemModel pageTreeItemModel = new PageTreeItemModel(
                    page.id(),
                    page.title(),
                    page.content());
            TreeItem<TreeItemModel> pageTreeItem = context.newTreeItem(pageTreeItemModel);
            context.addChild(treeItem, pageTreeItem);
        }
    }
}
