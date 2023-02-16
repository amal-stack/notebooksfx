package com.amalstack.notebooksfx.data.model;

import java.util.Collection;

public record Notebook(
        long id,
        String name,
        String username,
        String description,
        String creationTime,
        int sectionCount,
        int pageCount) {
    public record Contents(
            long id,
            String name,
            String username,
            String description,
            String creationTime,
            Collection<Section.Contents> sections
    ) {

    }
}
