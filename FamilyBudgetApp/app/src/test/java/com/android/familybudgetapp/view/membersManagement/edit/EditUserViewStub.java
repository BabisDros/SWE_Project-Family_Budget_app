package com.android.familybudgetapp.view.membersManagement.edit;

import com.android.familybudgetapp.view.ViewStub;

public class EditUserViewStub extends ViewStub implements EditUserView
{
    private String username;
    private String displayName;
    private String familyName;
    private boolean familyFieldDisabled;
    private int goToMemberManagementActivityCounter;

    @Override
    public void setUsernameField(String username)
    {
        this.username = username;
    }

    @Override
    public void setDisplayNameField(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public void setFamilyNameField(String familyName)
    {
        this.familyName = familyName;
    }

    @Override
    public void disableFamilyField()
    {
        familyFieldDisabled = true;
    }

    @Override
    public void goToMemberManagementActivity()
    {
        goToMemberManagementActivityCounter++;
    }

    public String getUsername()
    {
        return username;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public int getGoToMemberManagementActivityCounter()
    {
        return goToMemberManagementActivityCounter;
    }

    public boolean isFamilyFieldDisabled()
    {
        return familyFieldDisabled;
    }
}
