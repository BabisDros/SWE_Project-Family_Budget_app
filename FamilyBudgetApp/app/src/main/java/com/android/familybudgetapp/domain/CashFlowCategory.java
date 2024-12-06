package com.android.familybudgetapp.domain;

public abstract class CashFlowCategory
{
    private String name;

    public String getCategoryName()
    {
        return name;
    }

    public void setCategory(String name)
    {
        if(name==null)
        {
            throw new IllegalArgumentException("Name shouldn't be null");
        }
        this.name=name;
    }
}
