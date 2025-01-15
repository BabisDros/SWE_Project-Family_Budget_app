package com.android.familybudgetapp.domain;

public class Income extends CashFlowCategory
{
    /**
     * Creates an Income object with the provided name.
     * @param name The name to be assigned to the income
     */
    public Income(String name)
    {
        super.setName(name);
    }

    public String toString() {
        return "Income{name='" + getName() + "'}";
    }
}

