package com.android.familybudgetapp.view.authentication.login;

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
}
