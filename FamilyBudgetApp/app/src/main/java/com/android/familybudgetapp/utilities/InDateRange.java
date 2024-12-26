package com.android.familybudgetapp.utilities;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;

import java.time.YearMonth;

public class InDateRange {
    public static boolean cashFlowInMonthlyRange(CashFlow cashFlow)
    {
        if (cashFlow.getClass() == OneOff.class)
        {
            return YearMonth.from(cashFlow.getDateStart()).equals(YearMonth.now());
        } else
        {
            return !YearMonth.from(cashFlow.getDateStart()).isAfter(YearMonth.now()) &&
                    !YearMonth.from(YearMonth.now()).isAfter(YearMonth.from(((Repeating) cashFlow).getDateEnd()));
        }
    }

}
