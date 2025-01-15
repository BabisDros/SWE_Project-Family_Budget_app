package com.android.familybudgetapp.dao;

import com.android.familybudgetapp.domain.Family;

import java.util.Set;

public interface FamilyDAO
{
    /**
     * Find a family by id.
     * @param familyID Family's ID.
     * @return Family or {@code null} if family doesn't exist.
     */
    Family findByID(long familyID);

    /**
     * Saves a Family object to the external data source.
     * The object can either be a new entity that does not exist
     * in the data source or an existing entity whose state
     * is being updated.
     * @param entity The object whose state is being saved
     * to the external data source.
    */
    void save(Family entity);

    /**
     * Deletes the family object from the external data source.
     * @param entity The object to be deleted.
     */
    void delete(Family entity);

    /**
     * Retrieves all objects from the external data source.
     * @return The list of objects.
     */
    Set<Family> findAll();
}
