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
    public Collection<Notebook> findByUserId(Long userId) {
        return List.of(
                new Notebook(1L,
                        "My First Notebook",
                        LocalDateTime.now(),
                        random.nextInt(),
                        random.nextInt()),
                new Notebook(2L,
                        "My Second Notebook",
                        LocalDateTime.now(),
                        random.nextInt(),
                        random.nextInt())
        );
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
}
