package com.amalstack.notebooksfx.data.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Notebook {
    private long id;
    private String name;
    private LocalDateTime creationTime;
    private int sectionCount;
    private int pageCount;

    public Notebook(
            long id,
            String name,
            LocalDateTime creationTime,
            int sectionCount,
            int pageCount) {
        this.id = id;
        this.name = name;
        this.creationTime = creationTime;
        this.sectionCount = sectionCount;
        this.pageCount = pageCount;
    }

    public long getId() {
        return id;
    }

    public Notebook setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Notebook setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Notebook setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public int getSectionCount() {
        return sectionCount;
    }

    public Notebook setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
        return this;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Notebook setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Notebook) obj;
        return this.id == that.id &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.creationTime, that.creationTime) &&
                this.sectionCount == that.sectionCount &&
                this.pageCount == that.pageCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationTime, sectionCount, pageCount);
    }

    @Override
    public String toString() {
        return "Notebook[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "creationTime=" + creationTime + ", " +
                "sectionCount=" + sectionCount + ", " +
                "pageCount=" + pageCount + ']';
    }

}


