package com.amalstack.notebooksfx.data.repository;

import com.amalstack.notebooksfx.data.model.Section;
import com.amalstack.notebooksfx.data.model.SectionInput;

import java.util.Collection;

public interface SectionRepository {

    Collection<Section> findByNotebookId(Long notebookId);

    int countByNotebookId(Long notebookId);

    Section create(SectionInput sectionInput);

    void rename(Long sectionId, String name);

    void delete(Long sectionId);
}


