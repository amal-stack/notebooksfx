package com.amalstack.notebooksfx.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeView;
import javafx.util.StringConverter;

public abstract class TreeItemModel {
    private final long id;
    private final StringProperty name = new SimpleStringProperty(this, "name");

    public TreeItemModel(long id, String name) {
        this.id = id;
        this.name.set(name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public static class NameStringConverter extends StringConverter<TreeItemModel> {
        private final TreeView<TreeItemModel> treeView;

        private NameStringConverter(TreeView<TreeItemModel> treeView) {
            this.treeView = treeView;
        }

        public static NameStringConverter forTreeView(TreeView<TreeItemModel> treeView) {
            return new NameStringConverter(treeView);
        }

        @Override
        public String toString(TreeItemModel treeItemModel) {
            return treeItemModel.getName();
        }

        @Override
        public TreeItemModel fromString(String s) {
            var selectedItem = treeView.getSelectionModel().getSelectedItem();
            var value = selectedItem.getValue();
            value.setName(s);
            return value;
        }
    }
}
