package com.amalstack.notebooksfx;

import com.amalstack.notebooksfx.data.model.Notebook;
import com.amalstack.notebooksfx.data.repository.NotebookRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MockNotebookRepository implements NotebookRepository {
    final Random random = new Random();

    public MockNotebookRepository() {

    }


    @Override
    public Notebook find(Long id) {
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

    @Override
    public Collection<Notebook> findByCurrentUser() {
        return List.of(
                new Notebook(1L,
                        "My First Notebook",
                        "x",
                        "desc",
                        LocalDateTime.now().toString(),
                        random.nextInt(),
                        random.nextInt()),
                new Notebook(2L,
                        "My Second Notebook",
                        "x",
                        "desc",
                        LocalDateTime.now().toString(),
                        random.nextInt(),
                        random.nextInt())
        );
    }

    @Override
    public Notebook.Contents getContentsById(Long notebookId) {
        return null;
    }
}
