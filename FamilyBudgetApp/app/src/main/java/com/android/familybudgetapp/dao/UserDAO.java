package com.android.familybudgetapp.dao;

import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;

import java.util.Set;

public interface UserDAO
{
    /**
     * Find a user by id.
     * @param userID User's id.
     * @return User or {@code null} if user doesn't exist.
     */
    User findByID(long userID);

    /**
     * Find a user by username.
     * @param username User's username.
     * @return User or {@code null} if user doesn't exist.
     */
    User findByUsername(String username);
    /**
     * Saves a User object to the external data source.
     * The object can either be a new entity that does not exist
     * in the data source or an existing entity whose state
     * is being updated.
     * @param user The object whose state is being saved
     * to the external data source.
     */
    void save(Family family, User user);

    /**
     * Deletes the User object from the external data source.
     * @param entity The object to be deleted.
     */
    void delete(User entity);

    /**
     * Retrieves all objects from the external data source.
     * @return The list of objects.
     */
    Set<User> findAll();
}
