package com.android.familybudgetapp.view.membersManagement.registerCreate;

import com.android.familybudgetapp.view.ViewStub;

public class RegisterCreateViewStub extends ViewStub implements RegisterCreateView
{
    private String title;
    private String msg;
    private int goToMemberCounter;
    private boolean setupUI;
    private String familyName;
    private boolean fieldsCleared;

    @Override
    public void showAddMemberMessage(String title, String message)
    {
        this.title = title;
        this.msg = message;
    }

    @Override
    public void goToMemberManagement()
    {
        ++goToMemberCounter;
    }

    @Override
    public void setupUIToAddMemberMode()
    {
        setupUI = true;
    }

    @Override
    public void setFamilyNameField(String familyName)
    {
        this.familyName = familyName;
    }

    @Override
    public void clearFields()
    {
        fieldsCleared = true;
    }

    public String getAddMemberTitle()
    {
        return title;
    }

    public String getAddMemberMsg()
    {
        return msg;
    }

    public int getGoToMemberCounter()
    {
        return goToMemberCounter;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public boolean isFieldsCleared()
    {
        return fieldsCleared;
    }

    public boolean isUISetup()
    {
        return setupUI;
    }
}
