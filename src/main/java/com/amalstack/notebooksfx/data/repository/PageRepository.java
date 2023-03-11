package com.amalstack.notebooksfx.data.repository;

import com.amalstack.notebooksfx.data.model.Page;
import com.amalstack.notebooksfx.data.model.PageInput;

import java.util.Collection;

public interface PageRepository {

    Collection<Page> findByNotebookId(Long notebookId);

    Collection<Page> findBySectionId(Long sectionId);

    int countByNotebookId(Long notebookId);

    int countBySectionId(Long sectionId);

    void create(PageInput pageInput);

    void update(PageInput pageInput);

    void rename(Long pageId, String pageName);
}

