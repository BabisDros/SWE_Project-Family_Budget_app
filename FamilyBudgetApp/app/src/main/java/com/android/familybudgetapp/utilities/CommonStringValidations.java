package com.android.familybudgetapp.utilities;

public class CommonStringValidations
{
    public static final String INVALID_ALPHANUMERICAL = "Characters should be at least three." +
            "\nThe first and last characters must be a letter or a number. " +
            "\nBetween the first and last characters, there can be letters, numbers, or spaces.";


    /**
     * @param name Characters >= 2. First character alphanumerical and others alphanumerical or underscore.
     */
    public static boolean isUsernameValid(String name)
    {
        return name.matches("[a-zA-Z0-9][a-zA-Z0-9_]+");
    }


    /**
     * @param password Total characters at least 4. Alphanumerical.
     */
    public static boolean isPasswordValid(String password)
    {
        return password.matches("[a-zA-Z0-9]{4,}");
    }

    /**
     * @param name Characters >= 3. First and last character alphanumerical. Between them,
     *             alphanumerical or space.
     */
    public static boolean isAlphanumericWithSpaces(String name)
    {
        return name.matches("[a-zA-Z0-9][a-zA-Z0-9 ]+[a-zA-Z0-9]");
    }

    /**
     * @param amount Validate amount entered by android:inputType="numberDecimal"
     */
    public static boolean isAmountValid(String amount) {
        return !amount.isEmpty() &&
                !amount.equals("0");

    }


}
