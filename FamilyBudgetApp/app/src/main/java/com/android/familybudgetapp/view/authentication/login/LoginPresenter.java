package com.android.familybudgetapp.view.authentication.login;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginView>
{
    UserDAO userDAO;


    /**
     * Sets the User DAO.
     *
     * @param userDAO the {@link UserDAO} instance.
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * Checks the user's credentials and logs them in if valid.
     *
     * @param username the entered username.
     * @param password the entered password.
     */
    public void login(String username, String password)
    {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password))
        {
            Initializer.currentUserID= user.getID();
            view.goToHomepage(user.getFamilyPosition().name());
        }
        else
        {
            view.showErrorMessage("Wrong user credentials", "Username or password is wrong" +
                    " or user does not exist.");
        }
    }
}


