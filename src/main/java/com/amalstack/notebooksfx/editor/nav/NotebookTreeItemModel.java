package com.amalstack.notebooksfx.editor.nav;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NotebookTreeItemModel extends TreeItemModel {

    private final ObservableList<SectionTreeItemModel> sections = FXCollections.observableArrayList();

    public NotebookTreeItemModel(long id, String name) {
        super(id, name);
    }

    public ObservableList<SectionTreeItemModel> getSections() {
        return sections;
    }
}
