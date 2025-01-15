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

    /**
     * Changes Activity to MembersOverviewActivity.
     */
    void goToMemberManagement();

    /**
     * Changes UI to add a new member.
     */
    void setupUIToAddMemberMode();

    /**
     * Sets the familyName field with the provided name.
     *
     * @param familyName The name to be displayed.
     */
    void setFamilyNameField(String familyName);

    /**
     * Clears the fields to add a new member.
     */
    void clearFields();
}
