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
    boolean familyCreated = false;

    /**
     * Sets the User DAO.
     * @param userDAO the {@link UserDAO} instance.
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * Sets the Family DAO.
     * @param familyDAO the {@link FamilyDAO} instance.
     */
    public void setFamilyDAO(FamilyDAO familyDAO)
    {
        this.familyDAO = familyDAO;
    }

    /**
     * Validates input data and saves the user and family.
     * @param username the entered username.
     * @param password the entered password.
     * @param displayName the entered display name.
     * @param familyName the entered family name.
     */
    public void register(java.lang.String username, java.lang.String password, java.lang.String displayName, java.lang.String familyName)
    {
        if (!validateUsername(username)) return;
        if (!validatePassword(password)) return;
        if (!validateDisplayName(displayName)) return;
        if (!validateFamilyName(familyName)) return;

        Family family = new Family(familyName);

        familyDAO.save(family);

        User newUser;

        if (!familyCreated)
        {
            familyCreated=true;
            newUser = new User(displayName, username, password, FamPos.Protector, family);
        }
        else
        {
            newUser = new User(displayName, username, password, FamPos.Member, family);
        }

        userDAO.save(family, newUser);

        view.addMemberMessage("user: " + username + " added!", "Do you want to add a member?");
    }


    /**
     * Validates the entered username.
     * @param input the username to be validated.
     * @return {@code true} if the username is valid, otherwise {@code false}.
     */
    public boolean validateUsername(java.lang.String input)
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

    /**
     * Validates the entered password.
     * @param input the password to be validated.
     * @return {@code true} if the password is valid, otherwise {@code false}.
     */
    public boolean validatePassword(java.lang.String input)
    {
        if (!CommonStringValidations.isPasswordValid(input))
        {
            view.showErrorMessage("Wrong password format", "Password should be at least four numbers or letters without spaces.");
            return false;
        }
        return true;
    }

    /**
     * Validates the entered display name.
     * @param input the display name to be validated.
     * @return {@code true} if the display name is valid, otherwise {@code false}.
     */
    public boolean validateDisplayName(java.lang.String input)
    {
        if (!CommonStringValidations.isAlphanumericWithSpaces(input))
        {
            view.showErrorMessage("Invalid display name", "Name should be at least three characters." +
                    " First and last character letter or number. Between them, letter, number or space.");
            return false;
        }
        return true;
    }

    /**
     * Validates the entered family name.
     * @param input the family name to be validated.
     * @return {@code true} if the family name is valid, otherwise {@code false}.
     */
    public boolean validateFamilyName(java.lang.String input)
    {
        if (!CommonStringValidations.isAlphanumericWithSpaces(input))
        {
            view.showErrorMessage("Invalid family name", "Name should be at least three characters." +
                    " First and last character letter or number. Between them, letter, number or space.");
            return false;
        }
        return true;
    }
}


