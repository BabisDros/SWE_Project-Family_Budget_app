package com.android.familybudgetapp.view.membersManagement.overview;

import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.ViewStub;

import java.util.ArrayList;
import java.util.List;

public class MembersOverviewViewStub extends ViewStub implements MembersOverviewView
{
    private String title;
    private String msg;
    private List<User> members = new ArrayList<>();
    private int removedIndex;
    private int homepageActivityCounter;
    private int registerActivityCounter;
    boolean appExited;

    @Override
    public void populateMembersRecyclerView(List<User> members)
    {
        this.members = members;
    }

    @Override
    public void updateMembersRecyclerView(int removedIndex)
    {
        this.removedIndex = removedIndex;
    }

    @Override
    public void showDeleteAccountMessage(String title, String message)
    {
        this.title = title;
        this.msg = message;
    }

    @Override
    public void exitApp()
    {
        appExited = true;
    }

    @Override
    public void goToHomepageActivity()
    {
        homepageActivityCounter++;
    }

    @Override
    public void goToRegisterActivity()
    {
        registerActivityCounter++;
    }


    public String getTitle()
    {
        return title;
    }

    public String getMsg()
    {
        return msg;
    }

    public int getRemovedIndex()
    {
        return removedIndex;
    }

    public int getHomepageActivityCounter()
    {
        return homepageActivityCounter;
    }

    public int getRegisterActivityCounter()
    {
        return registerActivityCounter;
    }

    public List<User> getMembers()
    {
        return members;
    }
}
