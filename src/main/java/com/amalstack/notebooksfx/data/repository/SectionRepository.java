package com.amalstack.notebooksfx.data.repository;

import com.amalstack.notebooksfx.data.model.Section;

import java.util.Collection;

public class SectionRepository implements Repository<Section, Long> {
    @Override
    public Section find(Long id) {
        return null;
    }

    public Collection<Section> findByNotebookId(Long notebookId) {
        return null;
    }
    public int countByNotebookId(Long notebookId) {
        return 0;
    }
    @Override
    public void add(Section item) {

    }

    @Override
    public void update(Section item) {

    }

    @Override
    public void delete(Section item) {

    }
}
