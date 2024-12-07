package com.android.familybudgetapp.domain;

import java.time.LocalDateTime;
import java.util.Date;

public class OneOff extends CashFlow {
    public OneOff(int amount, CashFlowCategory category, LocalDateTime dateStart) {
        super(amount, category, dateStart);
    }
}
