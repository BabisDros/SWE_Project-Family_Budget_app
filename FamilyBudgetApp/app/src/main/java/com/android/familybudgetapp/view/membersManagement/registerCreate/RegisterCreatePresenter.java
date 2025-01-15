package com.android.familybudgetapp.view.membersManagement.registerCreate;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.membersManagement.BaseUserManagementPresenter;

public class RegisterCreatePresenter extends BaseUserManagementPresenter<RegisterCreateView>
{
    public static final String SUCCESSFUL_ADD_MEMBER_TITLE = "User: %s added!";
    public static final String ADD_MEMBER_PROMPT = "Do you want to add a new member?";
    private boolean addMemberModeEnabled;

    /**
     * Validates input data and creates a new family and user.
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
        completeUserAddition(newUser);
    }

    /**
     * Validates input data and creates a new user.
     *
     * @param username    the entered username.
     * @param password    the entered password.
     * @param displayName the entered display name.
     * @param familyName  the entered family name.
     */
    public void saveMember(String username, String password, String displayName, String familyName)
    {
        if (!validateAllFields(username, password, displayName, familyName)) return;
        User newUser = new User(displayName, username, password, FamPos.Member, family);
        completeUserAddition(newUser);
    }

    /**
     * Completes the addition of a new user to the family.
     * Adds the user to the family, saves the updated family and user data to the DAO,
     * and displays a message confirming the addition.
     *
     * @param user The user to be added.
     */
    private void completeUserAddition(User user)
    {
        family.addMember(user);
        familyDAO.save(family);
        userDAO.save(user);
        showAddMemberMessage(user.getUsername());
    }

    /**
     * Shows a message confirming the addition of a new User.
     * The message includes a prompt to add a new member.
     *
     * @param username The username of the member that was added.
     */
    private void showAddMemberMessage(String username)
    {
        view.showAddMemberMessage(String.format(SUCCESSFUL_ADD_MEMBER_TITLE, username), ADD_MEMBER_PROMPT);
    }

    @Override
    protected boolean validateUsernameUniqueness(String input)
    {
        if (userDAO.findByUsername(input) != null)
        {
            showUserExistsMsg();
            return false;
        }
        return true;
    }

    /**
     * Changes the UI to add a new member.
     * Initializes the user and family caches if not already enabled,
     * sets up the UI for adding a new member, and clears the input fields.
     */
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

    /**
     * Called when the NegativeButton of AddMemberDialog is clicked.
     */
    public void goToMemberManagement()
    {
        view.goToMemberManagement();
    }
}


