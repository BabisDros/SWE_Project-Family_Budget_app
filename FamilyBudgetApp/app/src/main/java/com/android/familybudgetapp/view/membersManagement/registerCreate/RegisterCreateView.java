package com.android.familybudgetapp.view.membersManagement.registerCreate;

import com.android.familybudgetapp.view.base.BaseView;

public interface RegisterCreateView extends BaseView
{
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
