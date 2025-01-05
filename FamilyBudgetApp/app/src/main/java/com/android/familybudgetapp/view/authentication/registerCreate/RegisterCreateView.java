package com.android.familybudgetapp.view.authentication.registerCreate;

import com.android.familybudgetapp.view.base.BaseView;

public interface RegisterCreateView extends BaseView
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
     * Shows a message when a new member is added.
     *
     * @param title   the title of the message.
     * @param message the content of the message.
     */
    void showAddMemberMessage(String title, String message);

    void goToMemberManagement();

    void setupUIToAddMemberMode();

    void setFamilyNameField(String familyName);

    void clearFields();
}
