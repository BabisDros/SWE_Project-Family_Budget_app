package com.android.familybudgetapp.view.authentication.login;

import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.CommonStringValidations;
import com.android.familybudgetapp.view.base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginView>
{
    UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public void login(String username, String password)
    {
        User user = userDAO.findByUsername(username);
        if(user!=null && user.getPassword().equals(password))
        {
            view.goToHomepage();
        }
        else
        {
            view.showErrorMessage("Wrong user credentials", "Username or password is wrong" +
                    " or user does not exist.");
        }
    }

    public void clear()
    {
        this.view = null;
    }
}


