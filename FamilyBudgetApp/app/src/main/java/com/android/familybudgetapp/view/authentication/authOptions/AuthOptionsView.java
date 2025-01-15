package com.android.familybudgetapp.view.authentication.authOptions;

import com.android.familybudgetapp.view.base.BaseView;

public interface AuthOptionsView extends BaseView
{

    /**
     * Changes activity to Login activity.
     */
    void goToLogin();

    /**
     * Changes activity to Register activity.
     */
    void goToRegister();
}
