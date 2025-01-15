package com.android.familybudgetapp.view;

import com.android.familybudgetapp.view.base.BaseView;

public abstract class ViewStub implements BaseView
{
    private String title;
    private String msg;


    @Override
    public void showErrorMessage(String title, String message)
    {
        this.title = title;
        this.msg = message;
    }

    public String getErrorTitle()
    {
        return title;
    }

    public String getErrorMsg()
    {
        return msg;
    }
}
