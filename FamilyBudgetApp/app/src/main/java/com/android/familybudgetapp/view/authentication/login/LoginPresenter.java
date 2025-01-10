package com.android.familybudgetapp.view.authentication.login;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.PBKDF2Hashing;
import com.android.familybudgetapp.view.base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginView>
{
    UserDAO userDAO;
    final String WRONG_CREDENTIALS_TITLE = "Wrong user credentials";
    final String WRONG_CREDENTIALS_MSG = "Username or password is wrong or user does not exist.";

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

        if (user == null)
        {
            view.showErrorMessage(WRONG_CREDENTIALS_TITLE, WRONG_CREDENTIALS_MSG);
            return;
        }

        try
        {
            boolean correctPassword = PBKDF2Hashing.verifyPassword(password, user.getPassword());
            if (correctPassword)
            {
                Initializer.currentUserID = user.getID();
                view.goToHomepage(user.getFamilyPosition().name());
            }
            else
            {
                view.showErrorMessage(WRONG_CREDENTIALS_TITLE, WRONG_CREDENTIALS_MSG);
            }
        }
        catch (Exception e)
        {
            view.showErrorMessage(WRONG_CREDENTIALS_TITLE, WRONG_CREDENTIALS_MSG);
        }
    }
}


