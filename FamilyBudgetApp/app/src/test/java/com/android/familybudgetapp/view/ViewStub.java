package com.android.familybudgetapp.view;

import com.android.familybudgetapp.view.base.BaseView;

public abstract class ViewStub implements BaseView
{
    private int errorCount;

    @Override
    public void showErrorMessage(String title, String message)
    {
        errorCount++;
    }

    public int getErrorCount()
    {
       return errorCount;
    }
}
