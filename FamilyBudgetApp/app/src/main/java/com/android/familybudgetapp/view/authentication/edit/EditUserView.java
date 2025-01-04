package com.android.familybudgetapp.view.authentication.edit;

import com.android.familybudgetapp.view.base.BaseView;

public interface EditUserView extends BaseView
{

    void setUsernameField(String username);

    void setPasswordField(String password);

    void setDisplayNameField(String displayName);

    void setFamilyNameField(String familyName);

    void disableFamilyField();

    void goToMemberManagementActivity();
}
