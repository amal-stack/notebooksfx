package com.amalstack.notebooksfx.data.repository;


import com.amalstack.notebooksfx.data.model.Notebook;

import java.util.Collection;

public interface NotebookRepository extends Repository<Notebook, Long> {
    Collection<Notebook> findByUserId(Long userId);
}



