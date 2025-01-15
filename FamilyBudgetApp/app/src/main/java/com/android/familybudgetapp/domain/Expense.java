package com.android.familybudgetapp.domain;

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
     * Sets the limit for the category. The limit must be a positive number.
     *
     * @param limit the limit to be set. A positive number.
     * @throws IllegalArgumentException if the limit is not a positive number
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
     * Validates if the provided limit is a positive number.
     *
     * @param limit the number to be validated
     * @return true if the limit is a positive number, false otherwise
     */
    public static boolean isLimitValid(int limit)
    {
        return limit > 0;
    }
}
