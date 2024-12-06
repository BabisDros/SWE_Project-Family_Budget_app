package com.android.familybudgetapp.domain;

public class Utilities
{
    public static boolean isUsernameValid(String name)
    {
       return  name.matches("[a-zA-Z0-9_]+");
    }

    public static boolean isAlphanumericWithSpaces(String name)
    {
        return  name.matches("[a-zA-Z0-9 ]+");
    }
}
