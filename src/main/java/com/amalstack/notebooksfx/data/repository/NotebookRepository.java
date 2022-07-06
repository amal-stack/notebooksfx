package com.amalstack.notebooksfx.data.repository;


import com.amalstack.notebooksfx.data.model.Notebook;

import java.util.Collection;

public class NotebookRepository implements Repository<Notebook, Long> {

    @Override
    public Notebook find(Long id) {
        return null;
    }

    public Collection<Notebook> findByUserId(Long userId) {
        return null;
    }

    @Override
    public void add(Notebook item) {

    }

    @Override
    public void update(Notebook item) {

    }

    @Override
    public void delete(Notebook item) {

    }
}


