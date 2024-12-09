package com.android.familybudgetapp.domain;

import androidx.annotation.IntRange;

public class Expense extends CashFlowCategory
{
    private int limit;

    /** Constructor
     * @param name alphanumerical value with spaces.
     * @param limit positive number
     */
    public Expense(String name, int limit)
    {
        super.setName(name);
        setLimit(limit);
    }

    public int getLimit()
    {
        return limit;
    }

    /**
     * @param limit positive number
     */
    public void setLimit(int limit)
    {
        if(!isLimitValid(limit))
        {
            throw new IllegalArgumentException("Limit isn't a positive number");
        }
        this.limit = limit;
    }

    /**
     * @param limit int
     */
    public static boolean isLimitValid(int limit)
    {
        return limit > 0;
    }
}
