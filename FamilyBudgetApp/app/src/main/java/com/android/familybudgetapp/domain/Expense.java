package com.android.familybudgetapp.domain;

public class Expense extends CashFlowCategory
{
    private int limit;

    public Expense(int limit)
    {
        setLimit(limit);
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        if(limit<0)
        {
            throw new IllegalArgumentException("Limit shouldn't be a negative number");
        }
        this.limit = limit;
    }
}
