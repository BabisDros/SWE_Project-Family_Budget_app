package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;

import com.android.familybudgetapp.view.ViewStub;

public class CashFlowCategoryCreateViewStub extends ViewStub implements CashFlowCategoryCreateView
{
    private int clearFieldsCounter;
    private String title;
    private String msg;

    @Override
    public void clearFields()
    {
        clearFieldsCounter++;
    }

    public int getClearFieldsCount()
    {
        return clearFieldsCounter;
    }

    @Override
    public void showAddCategoryMsg(String title, String message)
    {
        this.title = title;
        this.msg = message;
    }

    public String getTitle()
    {
        return title;
    }

    public String getMsg()
    {
        return msg;
    }
}
