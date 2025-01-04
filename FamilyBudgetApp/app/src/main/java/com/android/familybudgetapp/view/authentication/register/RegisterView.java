package com.android.familybudgetapp.view.authentication.register;

import com.android.familybudgetapp.view.base.BaseView;

public interface RegisterView extends BaseView
{
    /**
     * Gets the entered username from the EditText.
     * @return the username as a trimmed {@link String}.
     */
    String getUsername();

    /**
     * Gets the entered password from the EditText.
     * @return the password as a trimmed {@link String}.
     */
    String getPassword();

    /**
     * Gets the entered display name from the EditText.
     * @return the display name as a trimmed {@link String}.
     */
    String getDisplayName();

    /**
     * Gets the entered family name from the EditText.
     * @return the family name as a trimmed {@link String}.
     */
    String getFamilyName();

    /**
     * Shows a message when a new member is added.
     * @param title the title of the message.
     * @param message the content of the message.
     */
    void showAddMemberMessage(String title, String message);

    void goToMemberManagement();

    void setupUIToAddMemberMode(String familyName);
}
