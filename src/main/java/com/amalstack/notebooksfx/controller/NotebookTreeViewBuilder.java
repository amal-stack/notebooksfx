package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.DefaultGraphicNodeProvider;
import com.amalstack.notebooksfx.Graphic;
import com.amalstack.notebooksfx.builder.ControlBuilder;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;

import java.util.function.Consumer;

public class NotebookTreeViewBuilder implements ControlBuilder<TreeView<TreeItemModel>, NotebookTreeViewBuilder> {
    private final NotebookTreeItemModel model;
    private final DefaultGraphicNodeProvider graphicProvider = new DefaultGraphicNodeProvider();
    private ChangeListener<TreeItem<TreeItemModel>> selectListener;
    private EventHandler<TreeView.EditEvent<TreeItemModel>> editCommitEvent;
    private Consumer<TreeView<TreeItemModel>> config;
    private String id;

    private NotebookTreeViewBuilder(NotebookTreeItemModel model) {
        this.model = model;
    }

    public static NotebookTreeViewBuilder forModel(NotebookTreeItemModel model) {
        return new NotebookTreeViewBuilder(model);
    }

    public NotebookTreeViewBuilder onTreeItemSelect(ChangeListener<TreeItem<TreeItemModel>> listener) {
        this.selectListener = listener;
        return this;
    }

    public NotebookTreeViewBuilder onEditCommit(EventHandler<TreeView.EditEvent<TreeItemModel>> event) {
        this.editCommitEvent = event;
        return this;
    }

    @Override
    public NotebookTreeViewBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public NotebookTreeViewBuilder configure(Consumer<TreeView<TreeItemModel>> config) {
        this.config = config;
        return this;
    }

    public TreeView<TreeItemModel> build() {
        TreeItem<TreeItemModel> root = new TreeItem<>(model, graphicProvider.getNode(Graphic.NOTEBOOK));
        addSections(root);

        TreeView<TreeItemModel> treeView = new TreeView<>(root);
        treeView.setId(id);
        //addToolbarEditor(treeView);

        var stringConverter = TreeItemModel.NameStringConverter.forTreeView(treeView);
        treeView.setCellFactory(tv -> new TextFieldTreeCell<>(stringConverter));

        treeView.setOnEditCommit(editCommitEvent);
        treeView.getSelectionModel().selectedItemProperty().addListener(selectListener);

        applyConfiguration(treeView);

        return treeView;
    }

    private void addSections(TreeItem<TreeItemModel> root) {
        for (var section : model.getSections()) {
            TreeItem<TreeItemModel> sectionTreeItem = new TreeItem<>(section, graphicProvider.getNode(Graphic.SECTION));
            addPages(sectionTreeItem, section);
            root.getChildren().add(sectionTreeItem);
        }
    }

    private void addPages(TreeItem<TreeItemModel> sectionTreeItem, SectionTreeItemModel section) {
        for (var page : section.getPages()) {
            TreeItem<TreeItemModel> pageTreeItem = new TreeItem<>(page, graphicProvider.getNode(Graphic.PAGE));
            sectionTreeItem.getChildren().add(pageTreeItem);
        }
    }
    private void applyConfiguration(TreeView<TreeItemModel> treeView) {
        config.accept(treeView);
    }
}

