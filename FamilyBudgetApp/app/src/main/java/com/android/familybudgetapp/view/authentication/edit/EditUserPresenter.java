package com.android.familybudgetapp.view.authentication.edit;

import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.authentication.BaseUserManagementPresenter;

public class EditUserPresenter extends BaseUserManagementPresenter<EditUserView>
{
    public void setUserData(long userId)
    {
        User user = userDAO.findByID(userId);
        view.setUsernameField(user.getUsername());
        view.setPasswordField(user.getPassword());
        view.setDisplayNameField(user.getName());
        view.setfamilyNameField(user.getFamily().getName());
    }

    public void save()
    {
    }
}
