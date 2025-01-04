package com.android.familybudgetapp.memorydao;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;

public class MemoryInitializer extends Initializer {

    protected void eraseData()
    {
        for (User user: getUserDAO().findAll())
            getUserDAO().delete(user);
        for (Family family: getFamilyDAO().findAll())
            getFamilyDAO().delete(family);
    }


    public UserDAO getUserDAO()
    {
        return new UserDAOMemory();
    }

    public FamilyDAO getFamilyDAO()
    {
        return new FamilyDAOMemory();
    }
}
