package com.android.familybudgetapp.domain;

import java.time.LocalDateTime;
import java.util.Date;

public class CashFlow {

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
        this.amount = amount;
    }

    public void setCategory(CashFlowCategory category) {
        this.category = category;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
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
