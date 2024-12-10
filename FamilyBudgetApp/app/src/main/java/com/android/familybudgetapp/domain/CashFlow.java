package com.android.familybudgetapp.domain;

import java.time.LocalDateTime;

public abstract class CashFlow {

    private int amount;
    private CashFlowCategory category;
    private LocalDateTime dateStart;

    public CashFlow(int amount, CashFlowCategory category, LocalDateTime dateStart) {
        this.amount = amount;
        this.category = category;
        this.dateStart = dateStart;
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
        if (!validateAmount(amount)) {
            System.err.println("Invalid amount value: " + amount);
            throw new IllegalArgumentException("Amount is invalid.");
        }
        this.amount = amount;
    }

    public void setCategory(CashFlowCategory category) {
        this.category = category;
    }

    public void setDateStart(LocalDateTime dateStart) {
        if (!validateDateStart(dateStart)) {
            System.err.println("Invalid dateStart: " + dateStart);
            throw new IllegalArgumentException("dateStart is invalid.");
        }
        this.dateStart = dateStart;
    }

    private boolean validateAmount(int amount) {
        return (amount > 0);
    }

    public boolean validateDateStart(LocalDateTime start) {
        return (start != null) && !start.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "CashFlow{" +
                "amount=" + amount +
                ", category=" + category +
                ", dateStart=" + dateStart +
                '}';
    }
}
