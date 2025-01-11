package com.android.familybudgetapp.view.membersManagement.registerCreate;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.membersManagement.BaseUserManagementPresenter;

public class RegisterCreatePresenter extends BaseUserManagementPresenter<RegisterCreateView>
{
    boolean addMemberModeEnabled = false;
    final String SUCCESSFUL_ADD_MEMBER_MSG = "User: %s added!";
    final String ADD_MEMBER_PROMPT = "Do you want to add a new member?";

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
        if (!validateAllFields(username, password, displayName, familyName)) return;

        family = new Family(familyName);
        User newUser = new User(displayName, username, password, FamPos.Protector, family);
        protector = newUser;
        Initializer.currentUserID = newUser.getID();

        family.addMember(newUser);
        familyDAO.save(family);
        userDAO.save(newUser);
        showSuccessfulMessage(username);
    }

    public void addMember(String username, String password, String displayName, String familyName)
    {
        if (!validateAllFields(username, password, displayName, familyName)) return;

        User newUser = new User(displayName, username, password, FamPos.Member, family);

        family.addMember(newUser);
        familyDAO.save(family);
        userDAO.save(newUser);
        showSuccessfulMessage(username);
    }

    private void showSuccessfulMessage(String username)
    {
        view.showAddMemberMessage(String.format(SUCCESSFUL_ADD_MEMBER_MSG, username), ADD_MEMBER_PROMPT);
    }

    @Override
    public boolean validateUsernameUniqueness(String input)
    {
        if (userDAO.findByUsername(input) != null)
        {
            showUserExistsMsg();
            return false;
        }
        return true;
    }

    public void enableAddMemberMode()
    {
        if (!addMemberModeEnabled)
        {
            addMemberModeEnabled = true;
            protector = userDAO.findByID(Initializer.currentUserID);
            family = protector.getFamily();
            view.setupUIToAddMemberMode();
        }
        view.setFamilyNameField(family.getName());
        view.clearFields();
    }

    public void goToMemberManagement()
    {
        view.goToMemberManagement();
    }
}


