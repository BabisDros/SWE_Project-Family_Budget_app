package com.android.familybudgetapp.domain;

import java.time.LocalDateTime;
import java.time.YearMonth;

public abstract class CashFlow {

    private int amount;
    private CashFlowCategory category;
    private LocalDateTime dateStart;

    public CashFlow(int amount, CashFlowCategory category, LocalDateTime dateStart) {
        setAmount(amount);
        setCategory(category);
        setDateStart(dateStart);
    }
    public int getAmount() {
        return amount;
    }

    public CashFlowCategory getCategory() {
        return category;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setAmount(int amount) {
        if (!validateAmount(amount))
            throw new IllegalArgumentException("Amount is invalid.");
        this.amount = amount;
    }

    public void setCategory(CashFlowCategory category) {
        if (category == null)
            throw new IllegalArgumentException("Category can't be null");
        this.category = category;
    }

    public void setDateStart(LocalDateTime dateStart) {
        if (!validateDateStart(dateStart))
            throw new IllegalArgumentException("dateStart is invalid.");
        this.dateStart = dateStart;
    }

    public static boolean validateAmount(int amount) {
        return (amount > 0);
    }

    public static boolean validateDateStart(LocalDateTime start) {
        return (start != null) && !YearMonth.from(start).isBefore(YearMonth.now());
    }

    @Override
    public String toString() {
        return "CashFlow{" +
                "amount=" + amount +
                ", category=" + category +
                ", dateStart=" + dateStart +
                '}';
    }

    /**
     * Used ONLY to assist Junit tests with time Simulation
     */
    public void DebugSetDateStart(LocalDateTime date)
    {
        this.dateStart = date;
    }
}
