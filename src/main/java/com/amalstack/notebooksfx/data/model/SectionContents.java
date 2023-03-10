package com.amalstack.notebooksfx.data.model;

import java.util.Collection;

public record SectionContents(
        long id,
        String name,
        long notebookId,
        Collection<Page> pages
) {

}
