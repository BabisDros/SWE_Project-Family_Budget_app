package com.android.familybudgetapp.domain;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import android.util.Pair;

public class MonthlySurplus
{
    private LocalDateTime date;
    private int surplus;

    private ArrayList<Pair<Allowance, String>> allowanceMoneyBoxReasonPairs = new ArrayList<>();

    public MonthlySurplus(YearMonth date)
    {
        setDate(date);
    }

    public MonthlySurplus(YearMonth date, int surplus)
    {
        setDate(date);
        setSurplus(surplus);
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public int getSurplus()
    {
        return surplus;
    }

    public void setSurplus(int amount) {
        this.surplus = amount;
    }

    public void setDate(YearMonth yearMonth)
    {
        if(yearMonth==null)
        {
            throw new IllegalArgumentException("YearMonth shouldn't be null");
        }
        this.date = yearMonth.atEndOfMonth().atStartOfDay();
    }
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
        Pair<Allowance, String> pair = new Pair<>(allowance, reason);
        this.allowanceMoneyBoxReasonPairs.add(pair);
    }

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
