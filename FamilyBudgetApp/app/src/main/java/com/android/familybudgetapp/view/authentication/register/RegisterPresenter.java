package com.android.familybudgetapp.view.authentication.register;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.authentication.BaseUserManagementPresenter;

public class RegisterPresenter extends BaseUserManagementPresenter<RegisterView>
{
    /**
     * Validates input data and saves the user and family.
     *
     * @param username    the entered username.
     * @param password    the entered password.
     * @param displayName the entered display name.
     * @param familyName  the entered family name.
     */
    public void register(String username, String password, String displayName, String familyName)
    {
        if (!validateUsername(username)) return;
        if (!validatePassword(password)) return;
        if (!validateDisplayName(displayName)) return;
        if (!validateFamilyName(familyName)) return;

        User newUser;
        if (protector == null)
        {
            family = new Family(familyName);
            familyDAO.save(family);
            newUser = new User(displayName, username, password, FamPos.Protector, family);
            protector = newUser;

            //when registering the currentUserID should be the protector's
            Initializer.currentUserID = newUser.getID();
        }
        else
        {
            newUser = new User(displayName, username, password, FamPos.Member, family);
        }

        userDAO.save(family, newUser);
        view.showAddMemberMessage("user: " + username + " added!", "Do you want to add a member?");
    }

    public void onNoClicked()
    {
        view.goToMemberManagement();
    }

    @Override
    public boolean validateUsernameUniqueness(String input)
    {
        if (userDAO.findByUsername(input) != null)
        {
            view.showErrorMessage("Username already exists", "Please choose a different username.");
            return false;
        }
        return true;
    }
}


