package com.android.familybudgetapp.utilities;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;

import java.time.Year;
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

    public static boolean cashFlowInYearlyRange(CashFlow cashFlow)
    {
        if (cashFlow.getClass() == OneOff.class)
        {
            return Year.from(cashFlow.getDateStart()).equals(Year.now());
        } else
        {
            return !Year.from(cashFlow.getDateStart()).isAfter(Year.now()) &&
                    !Year.from(Year.now()).isAfter(Year.from(((Repeating) cashFlow).getDateEnd()));
        }
    }

}
