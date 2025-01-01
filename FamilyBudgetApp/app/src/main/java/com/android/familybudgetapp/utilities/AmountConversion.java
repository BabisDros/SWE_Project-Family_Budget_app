package com.android.familybudgetapp.utilities;

public class AmountConversion {

    public static String toEuro(int amount)
    {
        return amount / 100 + "," + Math.abs(amount) % 100 + "€";
    }
}
