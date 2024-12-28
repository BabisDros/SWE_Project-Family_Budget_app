package com.android.familybudgetapp.domain;


import java.time.LocalDateTime;


public class Allowance {
    private int amount;
    private LocalDateTime date = LocalDateTime.MIN;

    public Allowance(){}
    public Allowance(int amount, LocalDateTime date)
    {
        setAmount(amount);
        setDate(date);
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (!validateAmount(amount))
            throw new IllegalArgumentException("Amount for Allowance should be more than 0");
        this.amount = amount;
    }
    public static boolean validateAmount(int amount)
    {
        return amount > 0;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        if(date == null)
            throw new IllegalArgumentException("Date shouldn't be null");
        this.date = date;
    }
}
