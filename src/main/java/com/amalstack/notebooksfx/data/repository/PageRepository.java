package com.amalstack.notebooksfx.data.repository;

import com.amalstack.notebooksfx.data.model.Page;
import com.amalstack.notebooksfx.data.model.PageInput;

import java.util.Collection;

public interface PageRepository {

    Page findById(Long pageId);

    Collection<Page> findByNotebookId(Long notebookId);

    Collection<Page> findBySectionId(Long sectionId);

    int countByNotebookId(Long notebookId);

    int countBySectionId(Long sectionId);

    Page create(PageInput pageInput);

    void update(PageInput pageInput);

    void rename(Long pageId, String pageName);

    void setContent(Long pageId, String content);

    void delete(Long pageId);
}

