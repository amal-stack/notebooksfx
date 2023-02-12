package com.amalstack.notebooksfx.notebook;

import com.amalstack.notebooksfx.data.model.Notebook;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.format.DateTimeFormatter;

public class NotebookViewModel {
    private final long id;

    private final StringProperty name = new SimpleStringProperty(this, "name");

    private final StringProperty username = new SimpleStringProperty(this, "username");

    private final StringProperty creationTime = new SimpleStringProperty(this, "creationTime");

    private final IntegerProperty sectionCount = new SimpleIntegerProperty(this, "sectionCount");

    private final IntegerProperty pageCount = new SimpleIntegerProperty(this, "pageCount");

    public NotebookViewModel(
            long id,
            String name,
            String username,
            String creationTime,
            int sectionCount,
            int pageCount) {
        this.id = id;
        this.name.set(name);
        this.username.set(username);
        this.creationTime.set(creationTime);
        this.sectionCount.set(sectionCount);
        this.pageCount.set(pageCount);
        this.name.set(name);
    }

    public static NotebookViewModel fromNotebook(Notebook notebook) {
        return new NotebookViewModel(notebook.getId(),
                notebook.getName(),
                notebook.getName(),
                notebook.getCreationTime().format(DateTimeFormatter.ISO_DATE_TIME),
                notebook.getSectionCount(),
                notebook.getPageCount());
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

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getCreationTime() {
        return creationTime.get();
    }

    public void setCreationTime(String creationTime) {
        this.creationTime.set(creationTime);
    }

    public StringProperty creationTimeProperty() {
        return creationTime;
    }

    public int getSectionCount() {
        return sectionCount.get();
    }

    public void setSectionCount(int sectionCount) {
        this.sectionCount.set(sectionCount);
    }

    public IntegerProperty sectionCountProperty() {
        return sectionCount;
    }

    public int getPageCount() {
        return pageCount.get();
    }

    public IntegerProperty pageCountProperty() {
        return pageCount;
    }

    public long getId() {
        return id;
    }
}
