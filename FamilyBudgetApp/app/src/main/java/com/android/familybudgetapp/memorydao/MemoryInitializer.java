package com.android.familybudgetapp.memorydao;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;

public class MemoryInitializer extends Initializer {

    protected void eraseData()
    {

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
