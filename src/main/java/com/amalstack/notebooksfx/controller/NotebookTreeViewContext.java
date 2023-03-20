package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.Graphic;
import com.amalstack.notebooksfx.GraphicNodeProvider;
import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.data.model.NotebookContents;
import com.amalstack.notebooksfx.editor.controller.CreateSectionCommand;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;

public class NotebookTreeViewContext {

    private final TreeView<TreeItemModel> treeView;

    private final DataAccessService dataAccessService;

    private final GraphicNodeProvider graphicNodeProvider;

    public NotebookTreeViewContext(TreeView<TreeItemModel> treeView,
                                   DataAccessService dataAccessService,
                                   GraphicNodeProvider graphicNodeProvider) {
        this.treeView = treeView;
        this.dataAccessService = dataAccessService;
        this.graphicNodeProvider = graphicNodeProvider;
    }

    public void initialize(Long notebookId) {
        treeView.setCellFactory(this::cellFactory);
        NotebookTreeItemModel model = loadModel(notebookId);
        TreeItem<TreeItemModel> root = new TreeItem<>(model, getGraphicNode(Graphic.NOTEBOOK));
        addSections(root, model);
        treeView.setRoot(root);
        selectFirstPageIfPresent(treeView);
    }

    public TreeItem<TreeItemModel> newTreeItem(SectionTreeItemModel model) {
        return new TreeItem<>(model, getGraphicNode(Graphic.SECTION));
    }

    public TreeItem<TreeItemModel> newTreeItem(PageTreeItemModel model) {
        return new TreeItem<>(model, getGraphicNode(Graphic.PAGE));
    }

    public void addChild(TreeItem<TreeItemModel> parent, TreeItem<TreeItemModel> child) {
        parent.getChildren().add(child);
        treeView.requestFocus();
        treeView.getSelectionModel().select(child);
        int index = treeView.getSelectionModel().getSelectedIndex();
        treeView.scrollTo(index);
        treeView.layout();
        treeView.edit(child);
    }

    private void addSections(TreeItem<TreeItemModel> root, NotebookTreeItemModel notebook) {
        for (var section : notebook.getSections()) {
            TreeItem<TreeItemModel> sectionTreeItem = newTreeItem(section);
            addPages(sectionTreeItem, section);
            root.getChildren().add(sectionTreeItem);
        }
    }

    private void addPages(TreeItem<TreeItemModel> sectionTreeItem, SectionTreeItemModel section) {
        for (var page : section.getPages()) {
            TreeItem<TreeItemModel> pageTreeItem = newTreeItem(page);
            sectionTreeItem.getChildren().add(pageTreeItem);
        }
    }

    public TreeView<TreeItemModel> getTreeView() {
        return treeView;
    }

    public DataAccessService getDataAccessService() {
        return dataAccessService;
    }

    public Node getGraphicNode(Graphic graphic) {
        return graphicNodeProvider.getNode(graphic);
    }

    TreeCell<TreeItemModel> cellFactory(TreeView<TreeItemModel> treeView) {
        var treeCell = new TextFieldTreeCell<>(TreeItemModel.NameStringConverter.forTreeView(treeView));
        treeCell.contextMenuProperty().bind(Bindings.createObjectBinding(() -> {
            TreeItem<TreeItemModel> treeItem = treeCell.getTreeItem();
            if (treeItem == null) {
                return null;
            }
            TreeItemModel model = treeItem.getValue();
            if (model instanceof NotebookTreeItemModel) {
                return ContextMenuFactory.notebookContextMenu(this, treeItem);
            } else if (model instanceof SectionTreeItemModel) {
                return ContextMenuFactory.sectionContextMenu(this, treeItem);
            } else if (model instanceof PageTreeItemModel) {
                return ContextMenuFactory.pageContextMenu(this, treeItem);
            }
            return null;
        }, treeCell.treeItemProperty()));
        return treeCell;
    }

    private NotebookTreeItemModel loadModel(long notebookId) {
        NotebookContents notebookContents = dataAccessService
                .notebooks()
                .getContentsById(notebookId);

        var notebookTreeItemModel = new NotebookTreeItemModel(
                notebookContents.id(),
                notebookContents.name());

        notebookTreeItemModel.getSections()
                .addAll(notebookContents.sections()
                        .stream()
                        .map(c -> {
                            var model
                                    = SectionTreeItemModel.fromSectionContents(c);
                            model.getPages().addAll(c
                                    .pages()
                                    .stream()
                                    .map(PageTreeItemModel::fromPage)
                                    .toList());
                            return model;
                        })
                        .toList());

        return notebookTreeItemModel;
    }

    private void selectFirstPageIfPresent(TreeView<TreeItemModel> treeView) {
        treeView.getRoot().setExpanded(true);
        var sections = treeView.getRoot().getChildren();
        sections.stream()
                .filter(section -> !section.getChildren().isEmpty())
                .findFirst()
                .map(section -> section.getChildren().get(0))
                .ifPresent(page -> treeView
                        .getSelectionModel()
                        .select(page));                    // selecting also expands
    }

    private static class ContextMenuFactory {

        public static ContextMenu notebookContextMenu(NotebookTreeViewContext context, TreeItem<TreeItemModel> treeItem) {
            return new ContextMenu(
                    createMenuItem("Edit", context.getGraphicNode(Graphic.EDIT), editTreeItem(context.getTreeView(), treeItem)),
                    createMenuItem("New Section", context.getGraphicNode(Graphic.SECTION), createSection(context))
            );
        }

        public static ContextMenu sectionContextMenu(NotebookTreeViewContext context, TreeItem<TreeItemModel> treeItem) {
            return new ContextMenu(
                    createMenuItem("Edit", context.getGraphicNode(Graphic.EDIT), editTreeItem(context.getTreeView(), treeItem)),
                    createMenuItem("New Page", context.getGraphicNode(Graphic.PAGE), createPage(treeItem, context))
            );
        }

        public static ContextMenu pageContextMenu(NotebookTreeViewContext context, TreeItem<TreeItemModel> treeItem) {
            return new ContextMenu(
                    createMenuItem("Edit", context.getGraphicNode(Graphic.EDIT), editTreeItem(context.getTreeView(), treeItem))
            );
        }

        public static EventHandler<ActionEvent> editTreeItem(TreeView<TreeItemModel> treeView, TreeItem<TreeItemModel> treeItem) {
            return event -> CommandExecutor.execute(new EditTreeItemCommand(treeView, treeItem));
        }

        public static EventHandler<ActionEvent> createSection(NotebookTreeViewContext context) {
            return event -> CommandExecutor.execute(new CreateSectionCommand(context));
        }

        public static EventHandler<ActionEvent> createPage(TreeItem<TreeItemModel> sectionTreeItem, NotebookTreeViewContext context) {
            return event -> CommandExecutor.execute(new CreatePageCommand(sectionTreeItem, context));
        }

        private static MenuItem createMenuItem(String text, Node graphic, EventHandler<ActionEvent> eventHandler) {
            MenuItem menuItem = new MenuItem(text);
            menuItem.setGraphic(graphic);
            menuItem.setOnAction(eventHandler);
            return menuItem;
        }
    }
}
