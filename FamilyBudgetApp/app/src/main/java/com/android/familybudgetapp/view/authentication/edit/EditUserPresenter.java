package com.android.familybudgetapp.view.authentication.edit;

import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.authentication.BaseUserManagementPresenter;

public class EditUserPresenter extends BaseUserManagementPresenter<EditUserView>
{

    User userToEdit;

    public void setUserData(long userId)
    {
        userToEdit = userDAO.findByID(userId);
        view.setUsernameField(userToEdit.getUsername());
        view.setPasswordField(userToEdit.getPassword());
        view.setDisplayNameField(userToEdit.getName());
        view.setFamilyNameField(userToEdit.getFamily().getName());

        if (userToEdit.getFamilyPosition() == FamPos.Member)
        {
            view.disableFamilyField();
        }
    }

    public void save(String username, String password, String displayName, String familyName)
    {
        if (!validateUsername(username)) return;
        if (!validatePassword(password)) return;
        if (!validateDisplayName(displayName)) return;
        if (!validateFamilyName(familyName)) return;

        if (!familyName.equals(userToEdit.getFamily().getName()))
        {
            updateMembersFamilyName(familyName);
        }

        userToEdit.setUsername(username);
        userToEdit.setPassword(password);
        userToEdit.setName(displayName);
        userToEdit.getFamily().setName(familyName);

        view.goToMemberManagementActivity();
    }

    private void updateMembersFamilyName(String newFamilyName)
    {
        for (User user : userToEdit.getFamily().getMembers().values())
        {
            user.getFamily().setName(newFamilyName);
        }
    }

    @Override
    public boolean validateUsernameUniqueness(String input)
    {
        User userWithSameName = userDAO.findByUsername(input);
        if (userWithSameName != null && !userWithSameName.equals(userToEdit))
        {
            view.showErrorMessage("Username already exists", "Please choose a different username.");
            return false;
        }
        return true;
    }
}
