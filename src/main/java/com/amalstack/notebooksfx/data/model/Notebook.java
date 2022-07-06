package com.amalstack.notebooksfx.data.model;

import java.time.LocalDateTime;

public record Notebook(
        long id,
        String name,
        LocalDateTime creationTime,
        int sectionCount,
        int pageCount) {
}


