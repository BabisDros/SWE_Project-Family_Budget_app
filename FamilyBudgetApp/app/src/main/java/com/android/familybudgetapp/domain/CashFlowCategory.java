package com.android.familybudgetapp.domain;

import com.android.familybudgetapp.utilities.CommonStringValidations;

public abstract class CashFlowCategory
{
    protected String name;

    /**
     * @return the name of the category
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name alphanumerical value with spaces.
     */
    public void setName(String name)
    {
        if(name==null)
        {
            throw new IllegalArgumentException("Name shouldn't be null");
        }
        else if(!CommonStringValidations.isAlphanumericWithSpaces(name))
        {
            throw new IllegalArgumentException("Name should be consisted only by: Numbers, letters" +
                    " and spaces ONLY between them");
        }
        this.name=name;
    }
}
