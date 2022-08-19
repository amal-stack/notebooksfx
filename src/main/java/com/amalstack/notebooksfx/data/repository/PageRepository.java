package com.amalstack.notebooksfx.data.repository;

import com.amalstack.notebooksfx.data.model.Page;

import java.util.Collection;

public interface PageRepository extends Repository<Page, Long> {

    Collection<Page> findByNotebookId(Long notebookId);

    Collection<Page> findBySectionId(Long sectionId);

    int countByNotebookId(Long notebookId);

    int countBySectionId(Long sectionId);
}

