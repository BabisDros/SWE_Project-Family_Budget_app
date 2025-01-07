package com.android.familybudgetapp.view.membersManagement.edit;

import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.membersManagement.BaseUserManagementPresenter;

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
        if (!validateAllFields(username, password, displayName, familyName)) return;

        Family familyToUpdate = userToEdit.getFamily();
        if (!familyName.equals(familyToUpdate.getName()))
        {
            familyToUpdate.setName(familyName);
            familyDAO.save(familyToUpdate);// DAO's hashSet add, will replace the family
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
            showUserExistsMsg();
            return false;
        }
        return true;
    }
}
