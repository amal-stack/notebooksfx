package com.amalstack.notebooksfx.controller;

import com.amalstack.notebooksfx.data.model.Section;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SectionTreeItemModel extends TreeItemModel {
    private final ObservableList<PageTreeItemModel> pages = FXCollections.observableArrayList();

    public SectionTreeItemModel(long id, String name) {
        super(id, name);
    }

    public ObservableList<PageTreeItemModel> getPages() {
        return pages;
    }

    public static SectionTreeItemModel fromSectionContents(Section.Contents sectionContents) {
        return new SectionTreeItemModel(sectionContents.id(), sectionContents.name());
    }
}
