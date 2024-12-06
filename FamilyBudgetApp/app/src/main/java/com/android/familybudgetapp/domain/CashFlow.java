package com.android.familybudgetapp.domain;

import java.util.Date;

public class CashFlow {

    private int amount;
    private CashFlowCategory category;
    private Date dateStart;

    public CashFlow(int amount, CashFlowCategory category, Date dateStart) {
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategory(CashFlowCategory category) {
        this.category = category;
    }

    public void setDateStart(Date dateStart) {
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
