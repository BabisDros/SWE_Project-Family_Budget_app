package com.android.familybudgetapp.memorydao;

import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.User;


import java.util.HashSet;
import java.util.Set;

public class UserDAOMemory implements UserDAO
{
    protected static Set<User> users = new HashSet<>();
    @Override
    public void delete(User entity)
    {
        users.remove(entity);
    }

    @Override
    public Set<User> findAll()
    {
        return new HashSet<>(users);
    }

    @Override
    public void save(User entity)
    {
        users.add(entity);
    }

    @Override
    public User find(long userID)
    {
        for (User user : users)
        {
            if (user.getID() == userID)
            {
                return user;
            }
        }
        return null;
    }
}
