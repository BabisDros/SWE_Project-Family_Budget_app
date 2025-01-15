package com.android.familybudgetapp.domain;

import com.android.familybudgetapp.utilities.CommonStringValidations;

/**
 * Abstract class of a CashFlowCategory .
 */
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
     * Sets the name of the category. The name must be alphanumeric and can include spaces.
     *
     * @param name the name to be set
     * @throws IllegalArgumentException if the name is null or is not alphanumeric with spaces.
     */
    public void setName(String name)
    {
        if(name==null)
        {
            throw new IllegalArgumentException("Name shouldn't be null");
        }
        else if(!CommonStringValidations.isAlphanumericWithSpaces(name))
        {
            throw new IllegalArgumentException(CommonStringValidations.INVALID_ALPHANUMERICAL);
        }
        this.name=name;
    }
}
