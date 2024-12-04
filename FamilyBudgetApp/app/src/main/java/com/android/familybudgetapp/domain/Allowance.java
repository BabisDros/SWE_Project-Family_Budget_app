package com.android.familybudgetapp.domain;


import java.time.LocalDateTime;


public class Allowance {
    private int amount;
    private LocalDateTime date = LocalDateTime.MIN;

    Allowance(){}
    Allowance(int amount, LocalDateTime date)
    {
        if (amount < 0)
            return;
        this.amount = amount;
        this.date = date;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if (amount<0)
            return;
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
