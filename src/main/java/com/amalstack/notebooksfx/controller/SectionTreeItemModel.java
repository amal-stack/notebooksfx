package com.amalstack.notebooksfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SectionTreeItemModel extends TreeItemModel {
    private final ObservableList<PageTreeItemModel> pages = FXCollections.observableArrayList();

    public SectionTreeItemModel(int id, String name) {
        super(id, name);
    }

    public ObservableList<PageTreeItemModel> getPages() {
        return pages;
    }
}
