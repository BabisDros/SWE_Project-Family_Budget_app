package com.android.familybudgetapp.domain;
import com.android.familybudgetapp.utilities.Tuples;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;

/**
 * Class representing a family Monthly surplus amount with date,
 * and pairs of allowances with their reasons.
 */
public class MonthlySurplus
{
    private LocalDateTime date;
    private int surplus;

    private ArrayList<Tuples<Allowance, String>> allowanceMoneyBoxReasonPairs = new ArrayList<>();

    /**
     * Creates a MonthlySurplus object with the provided date.
     * The surplus is default to 0.
     *
     * @param date The date of the monthly surplus (as a {@link YearMonth} object)
     * @throws IllegalArgumentException if the date is null
     */
    public MonthlySurplus(YearMonth date)
    {
        setDate(date);
    }

    /**
     * Creates a MonthlySurplus object with the provided date. and surplus.
     *
     * @param date The date of the monthly surplus (as a {@link YearMonth} object)
     * @param surplus The surplus amount for the month
     * @throws IllegalArgumentException if the date is null
     */
    public MonthlySurplus(YearMonth date, int surplus)
    {
        setDate(date);
        setSurplus(surplus);
    }

    /**
     * Returns the date of the monthly surplus.
     *
     * @return The date as a {@link LocalDateTime} object
     */
    public LocalDateTime getDate()
    {
        return date;
    }

    /**
     * Returns the surplus amount  of the monthly surplus.
     *
     * @return The surplus amount
     */
    public int getSurplus()
    {
        return surplus;
    }
    /**
     * Sets the surplus amount for the monthly surplus.
     *
     * @param amount The new surplus amount
     */
    public void setSurplus(int amount) {
        this.surplus = amount;
    }

    /**
     * Sets the date of the monthly surplus.
     *
     * @param yearMonth The year and month of the surplus (as a {@link YearMonth} object)
     * @throws IllegalArgumentException if the yearMonth is null
     */
    public void setDate(YearMonth yearMonth)
    {
        if(yearMonth==null)
        {
            throw new IllegalArgumentException("YearMonth shouldn't be null");
        }
        this.date = yearMonth.atEndOfMonth().atStartOfDay();
    }

    /**
     * Adds a pair of allowance and reason to the list of allowance-reason pairs.
     *
     * @param allowance The allowance object
     * @param reason The reason of the allowance
     * @throws IllegalArgumentException if the allowance or reason is null
     */
    public void addAllowanceMoneyBoxPair(Allowance allowance, String reason)
    {
        if(allowance==null)
        {
            throw new IllegalArgumentException("Allowance shouldn't be null");
        }
        if(reason==null)
        {
            throw new IllegalArgumentException("Reason shouldn't be null");
        }
        Tuples<Allowance, String> pair = new Tuples<>(allowance, reason);
        this.allowanceMoneyBoxReasonPairs.add(pair);
    }

    public ArrayList<Tuples<Allowance, String>> getAllowanceMoneyBoxReasonPairs() {
        return allowanceMoneyBoxReasonPairs;
    }

    /**
     * Adds a cash flow amount to the monthly surplus.
     *
     * @param cashFlow The cash flow to be added
     * @throws IllegalArgumentException if the cash flow is null
     */
    public void addCashFlowToSurplus(CashFlow cashFlow)
    {
        if(cashFlow==null)
        {
            throw new IllegalArgumentException("CashFlow shouldn't be null");
        }
        this.surplus+=cashFlow.getMonthlyAmount(YearMonth.from(date));
    }

    public boolean validateCashFlow(CashFlow cashFlow)
    {
        if(cashFlow instanceof Repeating)
        {
            Repeating repeating=(Repeating)cashFlow;

            YearMonth cashFlowYearMonthDateEnd=YearMonth.of(repeating.getDateEnd().getYear(),repeating.getDateEnd().getMonth());
            YearMonth currentYearMonth= YearMonth.of(date.getYear(),date.getMonth());
            return currentYearMonth.isBefore(cashFlowYearMonthDateEnd);
        }
        else
        {
            OneOff oneOff = (OneOff) cashFlow;

            YearMonth cashFlowYearMonthDateEnd=YearMonth.of(oneOff.getDateStart().getYear(),oneOff.getDateStart().getMonth());
            YearMonth currentYearMonth= YearMonth.of(date.getYear(),date.getMonth());
            return currentYearMonth.isBefore(cashFlowYearMonthDateEnd);
        }
    }

    public void removeCashFlowFromSurplus(CashFlow cashFlow)
    {
        if(cashFlow==null)
        {
            throw new IllegalArgumentException("CashFlow shouldn't be null");
        }
        this.surplus-=cashFlow.getMonthlyAmount(YearMonth.from(date));
    }
}
