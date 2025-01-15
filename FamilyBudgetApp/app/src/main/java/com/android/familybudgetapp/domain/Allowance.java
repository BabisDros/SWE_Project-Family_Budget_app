package com.android.familybudgetapp.domain;


import java.time.LocalDateTime;


/**
 * The {@link Allowance} class allows the {@link FamPos#Protector Protector} to add money to a
 * specific {@link MoneyBox} of their choosing. It saves the current date in {@link LocalDateTime}
 * and amount of money given as an {@code int}
 */
public class Allowance {
    private int amount;
    private LocalDateTime date = LocalDateTime.MIN;

    public Allowance(){}
    public Allowance(int amount, LocalDateTime date)
    {
        setAmount(amount);
        setDate(date);
    }

    /**
     * Retrieves the current amount associated with this {@link Allowance} instance.
     *
     * @return the amount of money as an {@code int}
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount of money for the {@link Allowance} instance. The specified amount
     * must be greater than zero; otherwise, an {@link IllegalArgumentException} will be thrown.
     *
     * @param amount the amount of money to be set, specified as an {@code int}
     * @throws IllegalArgumentException if the provided amount is not greater than zero
     */
    public void setAmount(int amount) {
        if (!validateAmount(amount))
            throw new IllegalArgumentException("Amount for Allowance should be more than 0");
        this.amount = amount;
    }

    /**
     * Validates whether the specified amount is greater than zero.
     *
     * @param amount the amount of money to be validated, specified as an {@code int}
     * @return {@code true} if the amount is greater than zero, otherwise {@code false}
     */
    public static boolean validateAmount(int amount)
    {
        return amount > 0;
    }

    /**
     * Retrieves the date and time associated with this {@link Allowance} instance.
     *
     * @return the date and time as a {@link LocalDateTime} object
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the date for the {@link Allowance} instance. The specified date
     * must not be {@code null}; otherwise, an {@link IllegalArgumentException} will be thrown.
     *
     * @param date the date to be set, specified as a {@link LocalDateTime}
     * @throws IllegalArgumentException if the provided date is {@code null}
     */
    public void setDate(LocalDateTime date) {
        if(date == null)
            throw new IllegalArgumentException("Date shouldn't be null");
        this.date = date;
    }
}
