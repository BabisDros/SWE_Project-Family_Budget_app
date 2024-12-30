package com.android.familybudgetapp.view.authentication.login;

import com.android.familybudgetapp.view.base.BaseView;

public interface LoginView extends BaseView
{

    String getUsername();

    String getPassword();

    void goToHomepage();
}
