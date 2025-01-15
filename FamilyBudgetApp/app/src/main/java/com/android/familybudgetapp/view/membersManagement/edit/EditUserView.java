package com.android.familybudgetapp.view.membersManagement.edit;

import com.android.familybudgetapp.view.base.BaseView;

public interface EditUserView extends BaseView
{
    /**
     * Sets the username field with the provided username.
     *
     * @param username The username to be displayed.
     */
    void setUsernameField(String username);

    /**
     * Sets the displayName field with the provided name.
     *
     * @param displayName The name to be displayed.
     */
    void setDisplayNameField(String displayName);

    /**
     * Sets the familyName field with the provided name.
     *
     * @param familyName The name to be displayed.
     */
    void setFamilyNameField(String familyName);

    /**
     * Disables the family name field, preventing editing.
     */
    void disableFamilyField();

    /**
     * Changes Activity to MembersOverviewActivity.
     */
    void goToMemberManagementActivity();
}
