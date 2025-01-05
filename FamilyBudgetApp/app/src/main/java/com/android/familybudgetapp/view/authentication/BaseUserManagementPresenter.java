package com.android.familybudgetapp.view.authentication;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.CommonStringValidations;
import com.android.familybudgetapp.view.base.BasePresenter;
import com.android.familybudgetapp.view.base.BaseView;

public abstract class BaseUserManagementPresenter<V extends BaseView> extends BasePresenter<V>
{
    protected UserDAO userDAO;
    protected FamilyDAO familyDAO;
    protected User protector;

    protected Family family;

    final String USER_EXISTS = "User: %s added!";
    //region $DAO setup

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
     * Sets the Family DAO.
     *
     * @param familyDAO the {@link FamilyDAO} instance.
     */
    public void setFamilyDAO(FamilyDAO familyDAO)
    {
        this.familyDAO = familyDAO;
    }
    //endregion

    //region $Validations

    protected boolean validateAllFields(String username, String password, String displayName, String familyName)
    {
        return validateUsername(username) && validatePassword(password) && validateDisplayName(displayName) && validateFamilyName(familyName);
    }

    /**
     * Validates the entered username.
     *
     * @param input the username to be validated.
     * @return {@code true} if the username characters are valid and the name is unique, otherwise {@code false}.
     */
    public boolean validateUsername(String input)
    {
        if (!CommonStringValidations.isUsernameValid(input))
        {
            view.showErrorMessage("Wrong username format", "Characters should be at least two." +
                    " First character letter or number. Others letter, number or underscore");
            return false;
        }
        return validateUsernameUniqueness(input);
    }

    /**
     * Validates username's uniqueness.
     *
     * @param input the username to be validated.
     * @return {@code true} if the username is unique, otherwise {@code false}.
     */
    public abstract boolean validateUsernameUniqueness(String input);

    /**
     * Validates the entered password.
     *
     * @param input the password to be validated.
     * @return {@code true} if the password characters are valid, otherwise {@code false}.
     */
    public boolean validatePassword(String input)
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
     *
     * @param input the display name to be validated.
     * @return {@code true} if the display name characters are valid, otherwise {@code false}.
     */
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


    /**
     * Validates the entered family name.
     *
     * @param input the family name to be validated.
     * @return {@code true} if the family name is valid, otherwise {@code false}.
     */
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
    //endregion

    protected void showUserExistsMsg()
    {
        view.showErrorMessage("Username already exists", "Please choose a different username.");
    }
}


