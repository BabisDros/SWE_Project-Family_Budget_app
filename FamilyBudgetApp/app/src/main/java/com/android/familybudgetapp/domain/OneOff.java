package com.android.familybudgetapp.domain;

import java.time.LocalDateTime;

public class OneOff extends CashFlow {
    public OneOff(int amount, CashFlowCategory category, LocalDateTime dateStart) {
        super(amount, category, dateStart);
    }

    @Override
    public String toString() {
        return "OneOff{" +
                "amount=" + getAmount() +
                ", category=" + getCategory().toString() +
                ", dateStart=" + getDateStart() +
                '}';
    }
}
