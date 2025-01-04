package com.android.familybudgetapp.view.authentication.edit;

import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.authentication.BaseUserManagementPresenter;

public class EditUserPresenter extends BaseUserManagementPresenter<EditUserView>
{

    User user;

    public void setUserData(long userId)
    {
        user = userDAO.findByID(userId);
        view.setUsernameField(user.getUsername());
        view.setPasswordField(user.getPassword());
        view.setDisplayNameField(user.getName());
        view.setFamilyNameField(user.getFamily().getName());

        if (user.getFamilyPosition() == FamPos.Member)
        {
            view.disableFamilyField();
        }
    }

    public void onSave(String username, String password, String displayName, String familyName)
    {
        if (!validateUsername(username)) return;
        if (!validatePassword(password)) return;
        if (!validateDisplayName(displayName)) return;
        if (!validateFamilyName(familyName)) return;

        user.setUsername(username);
        user.setPassword(password);
        user.setName(displayName);
        user.getFamily().setName(familyName);
    }
}
