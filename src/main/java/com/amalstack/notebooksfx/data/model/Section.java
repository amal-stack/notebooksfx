package com.amalstack.notebooksfx.data.model;

import java.util.Objects;

public final class Section {
    private long id;
    private String name;
    private Notebook notebook;

    public Section(long id, String name, Notebook notebook) {
        this.id = id;
        this.name = name;
        this.notebook = notebook;
    }

    public long getId() {
        return id;
    }

    public Section setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Section setName(String name) {
        this.name = name;
        return this;
    }

    public Notebook getNotebook() {
        return notebook;
    }

    public Section setNotebook(Notebook notebook) {
        this.notebook = notebook;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Section) obj;
        return this.id == that.id &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.notebook, that.notebook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, notebook);
    }

    @Override
    public String toString() {
        return "Section[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "notebook=" + notebook + ']';
    }

}
