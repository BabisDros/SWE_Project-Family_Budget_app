package com.android.familybudgetapp.view.authentication.register;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.base.BaseViewModel;

public class RegisterViewModel extends BaseViewModel<RegisterPresenter>
{
    @Override
    protected RegisterPresenter createPresenter()
    {
        presenter = new RegisterPresenter();

        UserDAO userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();
        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);
        return presenter;
    }

    public RegisterPresenter getPresenter()
    {
        return presenter;
    }

    @Override
    protected void onCleared()
    {
        super.onCleared();
        presenter.clear();
    }
}
