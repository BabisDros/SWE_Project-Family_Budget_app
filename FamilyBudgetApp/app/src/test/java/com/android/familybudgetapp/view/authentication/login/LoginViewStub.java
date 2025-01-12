package com.android.familybudgetapp.view.authentication.login;

import com.android.familybudgetapp.view.ViewStub;

public class LoginViewStub extends ViewStub implements LoginView
{

    private int homepageCounter;
    @Override
    public void goToHomepage(String famPos)
    {
        homepageCounter++;
    }

    public int getHomepageCounter()
    {
        return homepageCounter;
    }

}
