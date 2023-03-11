package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.data.model.SectionContents;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SectionTreeItemModel extends TreeItemModel {
    private final ObservableList<PageTreeItemModel> pages = FXCollections.observableArrayList();

    public SectionTreeItemModel(long id, String name) {
        super(id, name);
    }

    public static SectionTreeItemModel fromSectionContents(SectionContents sectionContents) {
        return new SectionTreeItemModel(sectionContents.id(), sectionContents.name());
    }

    public ObservableList<PageTreeItemModel> getPages() {
        return pages;
    }
}
