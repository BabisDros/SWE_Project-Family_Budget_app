package com.android.familybudgetapp.view.authentication.authOptions;

import com.android.familybudgetapp.view.ViewStub;

public class AuthOptionsViewStub extends ViewStub implements AuthOptionsView
{
    private int loginCounter;
    private int registerCounter;

    @Override
    public void goToLogin()
    {
        loginCounter++;
    }

    @Override
    public void goToRegister()
    {
        registerCounter++;
    }

    public int getLoginNavigationCounter()
    {
        return loginCounter;
    }

    public int getRegisterNavigationCounter()
    {
        return registerCounter;
    }
}
