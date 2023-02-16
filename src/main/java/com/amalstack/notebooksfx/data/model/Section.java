package com.amalstack.notebooksfx.data.model;

import java.util.Collection;

public record Section(long id, String name, long notebookId) {
    public record Contents(
            long id,
            String name,
            long notebookId,
            Collection<Page> pages
    ) {

    }
}
