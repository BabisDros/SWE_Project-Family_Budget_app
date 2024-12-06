package com.android.familybudgetapp.domain;

import java.util.Date;

public class OneOff extends CashFlow {
    public OneOff(int amount, CashFlowCategory category, Date dateStart) {
        super(amount, category, dateStart);
    }
}
