package com.android.familybudgetapp.view.authentication.register;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.CommonStringValidations;
import com.android.familybudgetapp.view.base.BasePresenter;

public class RegisterPresenter extends BasePresenter<RegisterView>
{
    UserDAO userDAO;
    FamilyDAO familyDAO;

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public void setFamilyDAO(FamilyDAO familyDAO)
    {
        this.familyDAO = familyDAO;
    }

    public void register(String username, String password, String displayName, String familyName)
    {
        if(!validateUsername(username)) return;
        if(!validatePassword(password)) return;
        if(!validateDisplayName(displayName)) return;
        if(!validateFamilyName(familyName)) return;

        Family family = new Family(familyName);
        familyDAO.save(family);
        User newUser = new User(displayName, username, password, FamPos.Protector, family);
        userDAO.save(family, newUser);
        view.goToHomepage();
    }

    public boolean validateUsername(String input)
    {
        if (!CommonStringValidations.isUsernameValid(input))
        {
            view.showErrorMessage("Wrong username format", "Characters should be at least two." +
                    " First character letter or number. Others letter, number or underscore");
            return false;
        }

        if (userDAO.findByUsername(input) != null)
        {
            view.showErrorMessage("Username already exists", "Please choose a different username.");
            return false;
        }
        return true;
    }

    public boolean validatePassword(String input)
    {
        if (!CommonStringValidations.isPasswordValid(input))
        {
            view.showErrorMessage("Wrong password format", "Password should be at least four numbers or letters without spaces.");
            return  false;
        }
        return true;
    }

    public boolean validateDisplayName(String input)
    {
        if (!CommonStringValidations.isAlphanumericWithSpaces(input))
        {
            view.showErrorMessage("Invalid display name", "Name should be at least three characters." +
                    " First and last character letter or number. Between them, letter, number or space.");
            return false;
        }
        return true;
    }

    public boolean validateFamilyName(String input)
    {
        if (!CommonStringValidations.isAlphanumericWithSpaces(input))
        {
            view.showErrorMessage("Invalid family name", "Name should be at least three characters." +
                    " First and last character letter or number. Between them, letter, number or space.");
            return false;
        }
        return true;
    }

    public void clear()
    {
        this.view = null;
    }
}


