package com.amalstack.notebooksfx.data.repository;


import com.amalstack.notebooksfx.data.model.Notebook;
import com.amalstack.notebooksfx.data.model.NotebookContents;
import com.amalstack.notebooksfx.data.model.NotebookInput;

import java.util.Collection;

public interface NotebookRepository {
    Notebook findById(Long notebookId);

    Collection<Notebook> findByCurrentUser();

    NotebookContents getContentsById(Long notebookId);

    Notebook create(NotebookInput notebookInput);

    void update(long notebookId, NotebookInput notebookInput);

    void rename(long notebookId, String newName);

    void delete(Long notebookId);
}




