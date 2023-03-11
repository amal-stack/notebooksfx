package com.amalstack.notebooksfx.data.model;

import java.util.Collection;

public record NotebookContents(
        long id,
        String name,
        String username,
        String description,
        String creationTime,
        Collection<SectionContents> sections
) {

}
