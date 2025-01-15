package com.android.familybudgetapp.view.membersManagement.edit;

import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.membersManagement.BaseUserManagementPresenter;

import java.util.Objects;

public class EditUserPresenter extends BaseUserManagementPresenter<EditUserView>
{
    private User userToEdit;

    /**
     * Sets the user data for editing.
     * If the user is a member, the family field is disabled so that it cannot be edited.
     *
     * @param userId The ID of the user.
     */
    public void setUserData(long userId)
    {
        userToEdit = userDAO.findByID(userId);
        view.setUsernameField(userToEdit.getUsername());
        view.setDisplayNameField(userToEdit.getName());
        view.setFamilyNameField(userToEdit.getFamily().getName());

        if (userToEdit.getFamilyPosition() == FamPos.Member)
        {
            view.disableFamilyField();
        }
    }

    /**
     * Saves the edited user data.
     * Validates the input fields and updates the user and family DAO.
     * If the password is not empty, it updates the user's password.
     * At the end, it calls view to change to MembersOverviewActivity.
     *
     * @param username The new username for the user.
     * @param password The new password for the user.
     * @param displayName The new display name for the user.
     * @param familyName The new family name for the user's family.
     */
    public void save(String username, String password, String displayName, String familyName)
    {
        if (!validateAllFields(username, password, displayName, familyName)) return;

        Family familyToUpdate = userToEdit.getFamily();

        userToEdit.setUsername(username);
        //replace only when a new password is entered
        if (!password.isEmpty())
        {
            userToEdit.setPassword(password);
        }
        userToEdit.setName(displayName);
        familyToUpdate.setName(familyName);

        userDAO.save(userToEdit);
        // DAO's hashSet add method, will replace the family with edited user
        familyDAO.save(familyToUpdate);

        view.goToMemberManagementActivity();
    }


    /**
     * Validates the provided password.
     * If the password is unchanged (empty input), it is considered valid.
     * Else, it calls the super method validation to continue validation.
     *
     * @param input The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    @Override
    public boolean validatePassword(String input)
    {
        //unchanged password is valid
        if (Objects.equals(input, ""))
        {
            return true;
        }
        else return super.validatePassword(input);
    }

    /**
     * Validates if the given username is unique by checking if it already exists in the user DAO.
     * If a user with the same username exists and is not the user currently being edited,
     * a message is shown that the username already exists.
     *
     * @param input The username to validate.
     * @return true if the username is unique, false if it already exists.
     */
    @Override
    protected boolean validateUsernameUniqueness(String input)
    {
        User userWithSameName = userDAO.findByUsername(input);

        if (userWithSameName != null && !userWithSameName.equals(userToEdit))
        {
            showUserExistsMsg();
            return false;
        }
        return true;
    }
}
