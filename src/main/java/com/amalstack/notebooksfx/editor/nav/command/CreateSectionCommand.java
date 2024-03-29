package com.amalstack.notebooksfx.editor.nav.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.data.model.SectionInput;
import com.amalstack.notebooksfx.editor.nav.NotebookTreeViewContext;
import com.amalstack.notebooksfx.editor.nav.SectionTreeItemModel;

public class CreateSectionCommand implements Command {

    private final NotebookTreeViewContext context;


    public CreateSectionCommand(NotebookTreeViewContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        var notebookId = context.getTreeView()
                .getRoot()
                .getValue()
                .getId();

        var section = context.getDataAccessService()
                .sections()
                .create(new SectionInput("New Section", notebookId));

        var sectionTreeItemModel = new SectionTreeItemModel(section.id(), section.name());
        var sectionTreeItem = context.newTreeItem(sectionTreeItemModel);

        context.addChild(context.getTreeView().getRoot(), sectionTreeItem);
    }
}
