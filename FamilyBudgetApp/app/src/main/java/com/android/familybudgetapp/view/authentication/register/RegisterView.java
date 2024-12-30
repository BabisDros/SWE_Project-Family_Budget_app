package com.android.familybudgetapp.view.authentication.register;

import com.android.familybudgetapp.view.base.BaseView;

public interface RegisterView extends BaseView
{

    String getUsername();

    String getPassword();

    String getDisplayName();

    String getFamilyName();

    void goToHomepage();
}
