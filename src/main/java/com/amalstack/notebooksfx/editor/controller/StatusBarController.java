package com.amalstack.notebooksfx.editor.controller;

import com.amalstack.notebooksfx.editor.EditorContext;
import com.amalstack.notebooksfx.editor.nav.NotebookTreeViewContext;
import com.amalstack.notebooksfx.editor.nav.TreeItemModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;

public class StatusBarController {

    @FXML
    private Pane breadCrumbParent;
    @FXML
    private Label editorRowPositionLabel;

    @FXML
    private Label editorColumnPositionLabel;

    public void setContext(EditorContext editorContext, NotebookTreeViewContext notebookTreeViewContext) {
        addEditorCursorPositionListeners(editorContext);
        addBreadCrumbBar(notebookTreeViewContext);
    }

    private void addEditorCursorPositionListeners(EditorContext context) {
        var textArea = context.getEditorControlProvider().getEditorTextArea();
        textArea.currentParagraphProperty()
                .addListener(((observableValue, oldValue, newValue) ->
                        editorRowPositionLabel.setText(newValue.toString())));
        textArea.caretColumnProperty()
                .addListener(((observableValue, oldValue, newValue) ->
                        editorColumnPositionLabel.setText(newValue.toString())));
    }

    private void addBreadCrumbBar(NotebookTreeViewContext context) {
        var breadcrumb = createBreadCrumbBar(context.getTreeView());
        breadCrumbParent.getChildren().add(breadcrumb);
    }

    private BreadCrumbBar<TreeItemModel> createBreadCrumbBar(TreeView<TreeItemModel> treeView) {
        var breadcrumb = new BreadCrumbBar<>(treeView.getRoot());

        breadcrumb.setCrumbFactory(treeItem -> {
            var button = new BreadCrumbBar.BreadCrumbButton(treeItem.getValue().getName());
            button.setOnAction(actionEvent -> treeView
                    .getSelectionModel()
                    .select(treeItem));
            return button;
        });

        treeView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) ->
                        breadcrumb.setSelectedCrumb(newValue));

        breadcrumb.setOnCrumbAction(event -> {
            TreeItem<TreeItemModel> item = event.getSelectedCrumb();
            if (item != null) {
                treeView.getSelectionModel().select(item);
            }
        });

        return breadcrumb;
    }
}
