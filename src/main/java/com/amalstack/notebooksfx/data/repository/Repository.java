package com.amalstack.notebooksfx.data.repository;

/**
 * Defines a generic repository that manages an entity by interacting with a data source.
 *
 * @author Amal Krishna
 */
public interface Repository {
    /**
     * Finds an element by the provided id.
     *
     * @param <T> The type of the element.
     * @param <I> The type of the id.
     * @param id  The id of the element.
     * @return The element with the specified id.
     */
    <T, I> T findById(I id);

    /**
     * Persists a new item.
     *
     * @param <T>  The type of the item.
     * @param item The item to persist.
     */
    <T> void add(T item);

    /**
     * Updates an existing item.
     *
     * @param <T>  The type of the item.
     * @param item The item to update.
     */
    <T> void update(T item);

    /**
     * Deletes an existing item.
     *
     * @param id The id of the item to delete.
     */
    <I> void deleteById(I id);
}
