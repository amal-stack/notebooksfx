package com.amalstack.notebooksfx.editor.nav;

import com.amalstack.notebooksfx.command.CommandExecutor;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.data.model.NotebookContents;
import com.amalstack.notebooksfx.editor.nav.command.CreatePageCommand;
import com.amalstack.notebooksfx.editor.nav.command.CreateSectionCommand;
import com.amalstack.notebooksfx.editor.nav.command.DeleteTreeItemCommand;
import com.amalstack.notebooksfx.editor.nav.command.EditTreeItemCommand;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;

import java.util.ResourceBundle;

import static com.amalstack.notebooksfx.editor.nav.NotebookTreeViewContext.EventHandlers.*;

public class NotebookTreeViewContext {

    private final TreeView<TreeItemModel> treeView;

    private final DataAccessService dataAccessService;

    private final GraphicNodeProvider graphicNodeProvider;

    private final ResourceBundle resources;

    private final ReadOnlyObjectWrapper<PageTreeItemModel> currentPage = new ReadOnlyObjectWrapper<>();

    public NotebookTreeViewContext(TreeView<TreeItemModel> treeView,
                                   DataAccessService dataAccessService,
                                   GraphicNodeProvider graphicNodeProvider,
                                   ResourceBundle resources) {
        this.treeView = treeView;
        this.dataAccessService = dataAccessService;
        this.graphicNodeProvider = graphicNodeProvider;
        this.resources = resources;
    }

    public void initialize(Long notebookId) {
        treeView.setCellFactory(this::cellFactory);
        treeView.getSelectionModel()
                .selectedItemProperty()
                .addListener(this::onTreeItemSelect);
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

    public GraphicNodeProvider getGraphicNodeProvider() {
        return graphicNodeProvider;
    }

    public ResourceBundle getResources() {
        return resources;
    }

    public PageTreeItemModel getCurrentPage() {
        return currentPage.get();
    }

    public ReadOnlyObjectProperty<PageTreeItemModel> currentPageProperty() {
        return currentPage;
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

    private void onTreeItemSelect(
            ObservableValue<? extends TreeItem<TreeItemModel>> observableValue,
            TreeItem<TreeItemModel> previousItem,
            TreeItem<TreeItemModel> currentItem) {

        if (currentItem != null && currentItem.getValue() instanceof PageTreeItemModel page) {
            currentPage.set(page);
        }
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
                    createMenuItem(context.getResources().getString("editor.tree.context_menu.edit"), context.getGraphicNode(Graphic.EDIT), editTreeItem(context.getTreeView(), treeItem)),
                    createMenuItem(context.getResources().getString("editor.tree.context_menu.new_section"), context.getGraphicNode(Graphic.SECTION), createSection(context))
            );
        }

        public static ContextMenu sectionContextMenu(NotebookTreeViewContext context, TreeItem<TreeItemModel> treeItem) {
            return new ContextMenu(
                    createMenuItem(context.getResources().getString("editor.tree.context_menu.edit"), context.getGraphicNode(Graphic.EDIT), editTreeItem(context.getTreeView(), treeItem)),
                    createMenuItem(context.getResources().getString("editor.tree.context_menu.new_page"), context.getGraphicNode(Graphic.PAGE), createPage(treeItem, context)),
                    createMenuItem(context.getResources().getString("editor.tree.context_menu.delete"), context.getGraphicNode(Graphic.DELETE), deleteTreeItem(context, treeItem))
            );
        }

        public static ContextMenu pageContextMenu(NotebookTreeViewContext context, TreeItem<TreeItemModel> treeItem) {
            return new ContextMenu(
                    createMenuItem(context.getResources().getString("editor.tree.context_menu.edit"), context.getGraphicNode(Graphic.EDIT), editTreeItem(context.getTreeView(), treeItem)),
                    createMenuItem(context.getResources().getString("editor.tree.context_menu.delete"), context.getGraphicNode(Graphic.DELETE), deleteTreeItem(context, treeItem))
            );
        }


        private static MenuItem createMenuItem(String text, Node graphic, EventHandler<ActionEvent> eventHandler) {
            MenuItem menuItem = new MenuItem(text);
            menuItem.setGraphic(graphic);
            menuItem.setOnAction(eventHandler);
            return menuItem;
        }
    }

    public static class EventHandlers {
        public static EventHandler<ActionEvent> editTreeItem(TreeView<TreeItemModel> treeView, TreeItem<TreeItemModel> treeItem) {
            return event -> CommandExecutor.execute(new EditTreeItemCommand(treeView, treeItem));
        }

        public static EventHandler<ActionEvent> deleteTreeItem(NotebookTreeViewContext context, TreeItem<TreeItemModel> treeItem) {
            return event -> CommandExecutor.execute(new DeleteTreeItemCommand(context, treeItem));
        }

        public static EventHandler<ActionEvent> createSection(NotebookTreeViewContext context) {
            return event -> CommandExecutor.execute(new CreateSectionCommand(context));
        }

        public static EventHandler<ActionEvent> createPage(TreeItem<TreeItemModel> sectionTreeItem, NotebookTreeViewContext context) {
            return event -> CommandExecutor.execute(new CreatePageCommand(sectionTreeItem, context));
        }
    }
}
