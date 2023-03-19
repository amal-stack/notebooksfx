package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.Graphic;
import com.amalstack.notebooksfx.GraphicNodeProvider;
import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.controller.SectionTreeItemModel;
import com.amalstack.notebooksfx.controller.TreeItemModel;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.data.model.SectionInput;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class CreateSectionCommand implements Command {

    private final DataAccessService dataAccessService;

    private final TreeView<TreeItemModel> treeView;

    private final GraphicNodeProvider graphic;

    public CreateSectionCommand(DataAccessService dataAccessService,
                                TreeView<TreeItemModel> treeView,
                                GraphicNodeProvider graphic) {
        this.dataAccessService = dataAccessService;
        this.treeView = treeView;
        this.graphic = graphic;
    }

    @Override
    public void execute() {
        var notebookId = treeView.getRoot().getValue().getId();
        var section = dataAccessService.sections()
                .create(new SectionInput("New Section", notebookId));

        TreeItemModel sectionTreeItemModel = new SectionTreeItemModel(section.id(), section.name());

        var sectionTreeItem = new TreeItem<>(sectionTreeItemModel, graphic.getNode(Graphic.SECTION));

        // FIXME: This causes an IndexOutOfBounds exception
        treeView.getRoot().getChildren().add(sectionTreeItem);
        treeView.requestFocus();
        treeView.getSelectionModel().select(sectionTreeItem);
        int index = treeView.getSelectionModel().getSelectedIndex();
        treeView.scrollTo(index);
        treeView.layout();
        treeView.edit(sectionTreeItem);
    }
}
