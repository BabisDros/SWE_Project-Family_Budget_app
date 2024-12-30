package com.android.familybudgetapp.view.authentication.authOptions;

import com.android.familybudgetapp.view.base.BasePresenter;

public class AuthOptionsPresenter extends BasePresenter<AuthOptionsView>
{
    public void onLoginButtonClicked()
    {
        view.goToLogin();
    }

    public void onRegisterButtonClicked()
    {
        view.goToRegister();
    }
}
