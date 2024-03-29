package com.amalstack.notebooksfx.data.model;

public record Notebook(
        long id,
        String name,
        String username,
        String description,
        String creationTime,
        int sectionCount,
        int pageCount) {

}

