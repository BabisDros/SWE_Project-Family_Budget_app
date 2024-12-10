package com.android.familybudgetapp.utilities;

public class CommonStringValidations
{
    /**
     * @param name Characters >= 2. First character alphanumerical and others alphanumerical or underscore.
     */
    public static boolean isUsernameValid(String name)
    {
       return  name.matches("[a-zA-Z0-9][a-zA-Z0-9_]+");
    }

    /**
     * @param name Characters >= 3. First and last character alphanumerical. Between them,
     *   alphanumerical or underscore.
     */
    public static boolean isAlphanumericWithSpaces(String name)
    {
        return  name.matches("[a-zA-Z0-9][a-zA-Z0-9 ]+[a-zA-Z0-9]");
    }
}
