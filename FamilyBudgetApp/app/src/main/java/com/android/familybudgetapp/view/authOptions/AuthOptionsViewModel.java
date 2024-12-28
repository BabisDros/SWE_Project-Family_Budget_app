package com.android.familybudgetapp.view.authOptions;

import com.android.familybudgetapp.view.authOptions.AuthOptionsPresenter;
import androidx.lifecycle.ViewModel;
public class AuthOptionsViewModel extends ViewModel
{
    private AuthOptionsPresenter presenter;

    public AuthOptionsViewModel()
    {
        presenter = new AuthOptionsPresenter();
    }

    public AuthOptionsPresenter getPresenter()
    {
        return presenter;
    }

    @Override
    protected void onCleared()
    {

    }
}
