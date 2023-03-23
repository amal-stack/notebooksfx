package com.amalstack.notebooksfx.editor.nav;

import com.amalstack.notebooksfx.data.model.Page;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PageTreeItemModel extends TreeItemModel {

    private final StringProperty content = new SimpleStringProperty(this, "content");


    public PageTreeItemModel(long id, String name, String content) {
        super(id, name);
        this.content.set(content);
    }

    public static PageTreeItemModel fromPage(Page page) {
        return new PageTreeItemModel(page.id(), page.title(), page.content());
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
