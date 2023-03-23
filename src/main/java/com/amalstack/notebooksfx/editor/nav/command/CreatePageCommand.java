package com.amalstack.notebooksfx.editor.nav.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.data.model.PageInput;
import com.amalstack.notebooksfx.editor.nav.NotebookTreeViewContext;
import com.amalstack.notebooksfx.editor.nav.PageTreeItemModel;
import com.amalstack.notebooksfx.editor.nav.SectionTreeItemModel;
import com.amalstack.notebooksfx.editor.nav.TreeItemModel;
import javafx.scene.control.TreeItem;

public class CreatePageCommand implements Command {

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
