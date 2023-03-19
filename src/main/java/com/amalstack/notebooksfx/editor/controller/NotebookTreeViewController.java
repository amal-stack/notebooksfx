package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.GraphicNodeProvider;
import com.amalstack.notebooksfx.controller.*;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.data.model.NotebookContents;
import com.amalstack.notebooksfx.editor.RenameTreeItemCommand;
import com.amalstack.notebooksfx.util.ControllerParameters;
import com.amalstack.notebooksfx.util.ParameterizedController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NotebookTreeViewController implements ParameterizedController {

    private final GraphicNodeProvider graphic;

    private final DataAccessService dataAccessService;

    private final ReadOnlyObjectWrapper<PageTreeItemModel> currentPage = new ReadOnlyObjectWrapper<>();

    private ControllerParameters parameters;

    @FXML
    private VBox parent;
    @FXML
    private Button newSectionBtn;
    @FXML
    private ToolBar treeToolbar;

    public NotebookTreeViewController(DataAccessService dataAccessService,
                                      GraphicNodeProvider graphic) {
        this.dataAccessService = dataAccessService;
        this.graphic = graphic;
    }

    @Override
    public ControllerParameters getParameters() {
        return parameters;
    }

    @Override
    public void setParameters(ControllerParameters parameters) {
        this.parameters = parameters;
        onParametersSet();
    }

    private void onParametersSet() {
        Long notebookId = parameters.<Long>get("notebook.id").orElseThrow();
        NotebookTreeItemModel model = getModel(notebookId);
        initNotebookTreeView(model);
    }

    private NotebookTreeItemModel getModel(long notebookId) {
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

    private void initNotebookTreeView(NotebookTreeItemModel model) {
        var treeView = createTreeView(model);
        parent.getChildren().add(treeView);
        selectFirstPageIfPresent(treeView);
        newSectionBtn.setOnAction(Commands.eventHandler(
                new CreateSectionCommand(dataAccessService, treeView, graphic)));
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

    private TreeView<TreeItemModel> createTreeView(NotebookTreeItemModel model) {
        return NotebookTreeViewBuilder.forModel(model)
                .withId("notebookTreeView")
                .withGraphicNodeProvider(graphic)
                .onTreeItemSelect(this::onTreeItemSelect)
                .onEditCommit(this::onTreeViewEditCommit)
                .configure(treeView -> {
                    VBox.setVgrow(treeView, Priority.ALWAYS);
                    treeView.setShowRoot(true);
                    treeView.setEditable(true);
                })
                .build();
    }

    private void onTreeItemSelect(
            ObservableValue<? extends TreeItem<TreeItemModel>> observableValue,
            TreeItem<TreeItemModel> previousItem,
            TreeItem<TreeItemModel> currentItem) {
        if (previousItem != null && previousItem.getValue() instanceof PageTreeItemModel page) {
            /// Update content of previous page if changed
//            String previousContent = page.getContent();
//            String currentContent = editorTextArea.getText();
//            if (!previousContent.equals(currentContent)) {
//
//                page.setContent(currentContent);
//            }
        }
        if (currentItem != null && currentItem.getValue() instanceof PageTreeItemModel page) {
            currentPage.set(page);
            /// Update editor with content of current page
//            String content = page.getContent();
//            editorTextArea.replaceText(content);
//            previewHtml();
        }
    }

    private void onTreeViewEditCommit(TreeView.EditEvent<TreeItemModel> treeItemModelEditEvent) {
        new RenameTreeItemCommand(dataAccessService).execute(treeItemModelEditEvent.getOldValue());
    }


    public PageTreeItemModel getCurrentPage() {
        return currentPage.get();
    }

    public ReadOnlyObjectProperty<PageTreeItemModel> currentPageProperty() {
        return currentPage;
    }
}
