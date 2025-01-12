package com.android.familybudgetapp.view.cashFlowCategoryManagement.edit;

import com.android.familybudgetapp.view.ViewStub;

public class CashFlowCategoryEditViewStub extends ViewStub implements CashFlowCategoryEditView
{
    String name;
    String limit;
    private int overviewCounter;
    boolean spinnerSet;

    @Override
    public void setNameField(String name)
    {
        this.name = name;
    }

    @Override
    public void setLimitField(String limit)
    {
        this.limit = limit;
    }

    @Override
    public void goToOverview()
    {
        overviewCounter++;
    }

    @Override
    public void setSpinnerToExpense()
    {
        spinnerSet = true;
    }

    public String getName()
    {
        return name;
    }

    public String getLimit()
    {
        return limit;
    }

    public boolean isSpinnerSet()
    {
        return spinnerSet;
    }

    public int getOverviewCounter()
    {
        return overviewCounter;
    }
}
