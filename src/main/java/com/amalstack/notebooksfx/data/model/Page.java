package com.amalstack.notebooksfx.data.model;

import java.util.Objects;

public final class Page {
    private long id;
    private String title;
    private String content;
    private Section section;

    public Page(long id, String title, String content, Section section) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.section = section;
    }

    public long getId() {
        return id;
    }

    public Page setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Page setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Page setContent(String content) {
        this.content = content;
        return this;
    }

    public Section getSection() {
        return section;
    }

    public Page setSection(Section section) {
        this.section = section;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Page) obj;
        return this.id == that.id &&
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.content, that.content) &&
                Objects.equals(this.section, that.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, section);
    }

    @Override
    public String toString() {
        return "Page[" +
                "id=" + id + ", " +
                "title=" + title + ", " +
                "content=" + content + ", " +
                "section=" + section + ']';
    }

}
