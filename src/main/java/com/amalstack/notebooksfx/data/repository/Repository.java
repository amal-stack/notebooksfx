package com.amalstack.notebooksfx.data.repository;

/**
 * Defines a generic repository that manages an entity by interacting with a data source.
 * @param <T> The type of elements managed by the repository.
 * @param <I> The type of the id of the elements in the repository.
 */
public interface Repository<T, I> {
    /**
     * Finds an element by the provided id.
     * @param id The id of the element.
     * @return The element with the specified id.
     */
    T find(I id);

    /**
     * Persists a new item.
     * @param item The item to persist.
     */
    void add(T item);

    /**
     * Updates an existing item.
     * @param item The item to update.
     */
    void update(T item);

    /**
     * Deletes an existing item.
     * @param item The item to delete.
     */
    void delete(T item);
}
