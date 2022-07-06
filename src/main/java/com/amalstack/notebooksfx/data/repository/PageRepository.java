package com.amalstack.notebooksfx.data.repository;

import com.amalstack.notebooksfx.data.model.Page;

import java.util.Collection;

public class PageRepository implements Repository<Page, Long> {
    @Override
    public Page find(Long id) {
        return null;
    }

    public Collection<Page> findByNotebookId(Long notebookId) {
        return null;
    }

    public Collection<Page> findBySectionId(Long sectionId) {
        return null;
    }
    public int countByNotebookId(Long notebookId) {
        return 0;
    }

    public int countBySectionId(Long sectionId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Page item) {

    }

    @Override
    public void update(Page item) {

    }

    @Override
    public void delete(Page item) {

    }
}

