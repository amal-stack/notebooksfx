package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.GraphicNodeProvider;
import com.amalstack.notebooksfx.controller.*;
import com.amalstack.notebooksfx.data.model.Notebook;
import com.amalstack.notebooksfx.data.repository.NotebookRepository;
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

    private final NotebookRepository notebookRepository;

    private final ReadOnlyObjectWrapper<PageTreeItemModel> currentPage = new ReadOnlyObjectWrapper<>();

    private ControllerParameters parameters;

    @FXML
    private VBox parent;
    @FXML
    private Button newSectionBtn;
    @FXML
    private ToolBar treeToolbar;

    public NotebookTreeViewController(NotebookRepository notebookRepository,
                                      GraphicNodeProvider graphic) {
        this.notebookRepository = notebookRepository;
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
        Notebook.Contents notebookContents = notebookRepository.getContentsById(notebookId);

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
    }

    private void selectFirstPageIfPresent(TreeView<TreeItemModel> treeView) {
        treeView.getRoot().setExpanded(true);
        var sections = treeView.getRoot().getChildren();
        sections.stream()
                .filter(section -> !section.getChildren().isEmpty())
                .findFirst()
                .ifPresent(section -> treeView
                        .getSelectionModel()
                        .select(section.getChildren().get(0)));
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
        //TODO: Save name change
    }

    private NotebookTreeItemModel getTestModel() {
        var treeItem = new NotebookTreeItemModel(1, "Current");
        treeItem.getSections()
                .addAll(IntStream.rangeClosed(1, 5).mapToObj(i -> {
                    var section = new SectionTreeItemModel(i, "Section " + i);
                    section.getPages()
                            .addAll(IntStream.rangeClosed(1, 5)
                                    .mapToObj(j ->
                                            new PageTreeItemModel(j,
                                                    "Page " + i + " . " + j,
                                                    "Content of Page **" + i + "." + j + "**. ++Hello++"))
                                    .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    return section;
                }).toList());
        return treeItem;
    }

    public PageTreeItemModel getCurrentPage() {
        return currentPage.get();
    }

    public ReadOnlyObjectProperty<PageTreeItemModel> currentPageProperty() {
        return currentPage;
    }
}
