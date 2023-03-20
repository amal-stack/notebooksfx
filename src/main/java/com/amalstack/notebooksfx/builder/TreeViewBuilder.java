package com.amalstack.notebooksfx.builder;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

import java.util.function.Consumer;

public class TreeViewBuilder<T> implements ControlBuilder<TreeView<T>, TreeViewBuilder<T>> {
    private ChangeListener<TreeItem<T>> selectListener;
    private EventHandler<TreeView.EditEvent<T>> editCommitEvent;
    private Consumer<TreeView<T>> config;
    private Callback<TreeView<T>, TreeCell<T>> cellFactory;
    private String id;

    public TreeViewBuilder<T> onTreeItemSelect(ChangeListener<TreeItem<T>> listener) {
        this.selectListener = listener;
        return this;
    }

    public TreeViewBuilder<T> onEditCommit(EventHandler<TreeView.EditEvent<T>> event) {
        this.editCommitEvent = event;
        return this;
    }

    public TreeViewBuilder<T> withCellFactory(Callback<TreeView<T>, TreeCell<T>> cellFactory) {
        this.cellFactory = cellFactory;
        return this;
    }

    @Override
    public TreeViewBuilder<T> withId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public TreeViewBuilder<T> configure(Consumer<TreeView<T>> config) {
        this.config = config;
        return this;
    }

    public TreeView<T> build() {
        TreeView<T> treeView = new TreeView<>();
        if (id != null) {
            treeView.setId(id);
        }
        if (cellFactory != null) {
            treeView.setCellFactory(cellFactory);
        }
        if (selectListener != null) {
            treeView.getSelectionModel()
                    .selectedItemProperty()
                    .addListener(selectListener);
        }
        if (editCommitEvent != null) {
            treeView.setOnEditCommit(editCommitEvent);
        }
        if (config != null) {
            config.accept(treeView);
        }
        return treeView;
    }
}
