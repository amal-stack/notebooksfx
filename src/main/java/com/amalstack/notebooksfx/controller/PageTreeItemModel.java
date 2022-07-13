package com.amalstack.notebooksfx.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PageTreeItemModel extends TreeItemModel {
    private final StringProperty content = new SimpleStringProperty(this, "content");

    public PageTreeItemModel(int id, String name, String content) {
        super(id, name);
        this.content.set(content);
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public StringProperty contentProperty() {
        return content;
    }
}
