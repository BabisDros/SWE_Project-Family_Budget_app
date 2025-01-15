package com.android.familybudgetapp.domain;

public class Income extends CashFlowCategory
{
    /**
     * @param name alphanumerical value
     */
    public Income(String name)
    {
        super.setName(name);
    }

    public String toString() {
        return "Income{name='" + getName() + "'}";
    }
}

