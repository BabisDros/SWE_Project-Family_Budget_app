package com.android.familybudgetapp.view.authentication.registerCreate;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.base.BaseViewModel;

public class RegisterCreateViewModel extends BaseViewModel<RegisterCreatePresenter>
{
    @Override
    protected RegisterCreatePresenter createPresenter()
    {
        presenter = new RegisterCreatePresenter();

        UserDAO userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();

        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);
        return presenter;
    }
}
