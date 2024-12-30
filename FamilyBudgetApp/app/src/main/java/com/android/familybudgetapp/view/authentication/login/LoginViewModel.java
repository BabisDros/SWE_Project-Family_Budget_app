package com.android.familybudgetapp.view.authentication.login;

import androidx.lifecycle.ViewModel;

import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel<LoginPresenter>
{
    @Override
    protected LoginPresenter createPresenter()
    {
        UserDAO userDAO = new UserDAOMemory();
        presenter = new LoginPresenter();
        presenter.setUserDAO(userDAO);
        return presenter;
    }

    public LoginPresenter getPresenter()
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
