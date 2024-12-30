package com.android.familybudgetapp.view.authentication.authOptions;

import com.android.familybudgetapp.view.base.BaseViewModel;

public class AuthOptionsViewModel extends BaseViewModel<AuthOptionsPresenter>
{

    @Override
    protected AuthOptionsPresenter createPresenter()
    {
        return new AuthOptionsPresenter();
    }

}
