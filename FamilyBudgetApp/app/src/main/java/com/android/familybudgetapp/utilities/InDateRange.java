package com.android.familybudgetapp.utilities;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;

import java.time.Year;
import java.time.YearMonth;

public class InDateRange {
    /**
     * Determines whether a given {@link CashFlow} object falls within the current month.
     * For a OneOff {@link CashFlow}, it checks if the start date's month is the current month.
     * For a Repeating {@link CashFlow}, it checks if the current month lies within the range
     * from the start date to the end date.
     *
     * @param cashFlow the {@link CashFlow} object to evaluate. It can be of type OneOff or Repeating.
     * @return true if the {@link CashFlow} falls within the current month; false otherwise.
     */
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

    /**
     * Determines whether a given {@link CashFlow} object falls within the current year.
     * For a OneOff {@link CashFlow}, it checks if the start date's year is the current year.
     * For a Repeating {@link CashFlow}, it checks if the current year lies within the range
     * from the start year to the end year.
     *
     * @param cashFlow the {@link CashFlow} object to evaluate. It can be of type OneOff or Repeating.
     * @return true if the {@link CashFlow} falls within the current year; false otherwise.
     */
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
