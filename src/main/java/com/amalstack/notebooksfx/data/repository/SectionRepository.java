package com.amalstack.notebooksfx.data.repository;

import com.amalstack.notebooksfx.data.model.Section;
import com.amalstack.notebooksfx.data.model.SectionInput;

import java.util.Collection;

public interface SectionRepository {

    Collection<Section> findByNotebookId(Long notebookId);

    int countByNotebookId(Long notebookId);

    void create(SectionInput sectionInput);

    void update(SectionInput sectionInput);

}


