package com.amalstack.notebooksfx.data;

public interface Repository<T, I> {
    T findById(I id);
    void add(T item);
    void update(T item);
    void delete(T item);
}
