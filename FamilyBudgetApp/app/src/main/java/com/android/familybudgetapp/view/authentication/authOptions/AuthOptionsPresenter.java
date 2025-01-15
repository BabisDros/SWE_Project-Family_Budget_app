package com.android.familybudgetapp.view.authentication.authOptions;

import com.android.familybudgetapp.view.base.BasePresenter;

public class AuthOptionsPresenter extends BasePresenter<AuthOptionsView>
{
    /**
     * Called when the login button is clicked.
     */
    public void navigateToLogin()
    {
        view.goToLogin();
    }

    /**
     * Called when the register button is clicked.
     */
    public void navigateToRegister()
    {
        view.goToRegister();
    }
}
