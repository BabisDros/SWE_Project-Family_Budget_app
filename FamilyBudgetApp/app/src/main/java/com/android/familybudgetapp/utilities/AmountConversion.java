package com.android.familybudgetapp.utilities;

public class AmountConversion {

    /**
     * Converts an amount in cents to a euro string representation.
     *
     * @param amount the amount in cents to be converted
     * @return a string representation of the amount in euros
     */
    public static String toEuro(int amount)
    {
        return amount / 100 + "," + Math.abs(amount) % 100 + "€";
    }
}
