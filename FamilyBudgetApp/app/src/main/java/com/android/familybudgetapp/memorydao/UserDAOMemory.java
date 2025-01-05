package com.android.familybudgetapp.memorydao;

import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import java.util.HashSet;
import java.util.Set;

public class UserDAOMemory implements UserDAO
{
    protected static Set<User> users = new HashSet<>();
    @Override
    public void delete(User user)
    {
        user.getFamily().removeMember(user);
        users.remove(user);
    }

    @Override
    public Set<User> findAll()
    {
        return new HashSet<>(users);
    }

    @Override
    public void save(Family family,  User user)
    {
        family.addMember(user);
        user.setFamily(family);
        users.add(user);
    }

    @Override
    public User findByID(long userID)
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

    public User findByUsername(String username)
    {
        for (User user : users)
        {
            if (user.getUsername().equalsIgnoreCase(username))
            {
                return user;
            }
        }
        return null;
    }
}
