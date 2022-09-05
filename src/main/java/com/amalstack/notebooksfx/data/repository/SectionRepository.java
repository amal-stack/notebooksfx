package com.amalstack.notebooksfx.data.repository;

import com.amalstack.notebooksfx.data.model.Section;

import java.util.Collection;

public interface SectionRepository extends Repository<Section, Long> {

    Collection<Section> findByNotebookId(Long notebookId);

    int countByNotebookId(Long notebookId);
}


