package com.android.familybudgetapp.view.authentication.login;

import com.android.familybudgetapp.view.base.BaseView;

public interface LoginView extends BaseView
{
    /**
     * Gets the entered username from the EditText.
     *
     * @return the username as a trimmed {@link String}.
     */
    String getUsername();

    /**
     * Gets the entered password from the EditText.
     *
     * @return the password as a trimmed {@link String}.
     */
    String getPassword();

    /**
     * Changes Activity to Homepage.
     */
    void goToHomepage(String famPos);
}
