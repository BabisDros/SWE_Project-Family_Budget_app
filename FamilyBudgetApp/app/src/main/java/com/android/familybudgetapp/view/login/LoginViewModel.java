package com.android.familybudgetapp.view.login;

import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel
{
    private  LoginPresenter presenter;

    public LoginViewModel()
    {
        presenter = new LoginPresenter();
    }

    public LoginPresenter getPresenter()
    {
        return presenter;
    }

    @Override
    protected void onCleared()
    {

    }
}
