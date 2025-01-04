package com.android.familybudgetapp.view.authentication.edit;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;

import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.base.BaseViewModel;

public class EditUserViewModel extends BaseViewModel<EditUserPresenter>
{
    @Override
    protected EditUserPresenter createPresenter()
    {
        presenter = new EditUserPresenter();

        UserDAO userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();

        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);
        return presenter;
    }
}
