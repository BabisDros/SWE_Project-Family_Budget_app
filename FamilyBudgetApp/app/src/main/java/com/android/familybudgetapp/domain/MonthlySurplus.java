package com.android.familybudgetapp.domain;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class MonthlySurplus
{
    private LocalDateTime date;
    private int surplus;

    public MonthlySurplus(LocalDateTime date)
    {
        setDate(date);
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public int getSurplus()
    {
        return surplus;
    }

    public void setDate(LocalDateTime datetime)
    {
        if(datetime==null)
        {
            throw new IllegalArgumentException("Datetime shouldn't be null");
        }
        this.date = datetime;
    }

    public void addCashFlowToSurplus(CashFlow cashFlow)
    {
        if(cashFlow==null)
        {
            throw new IllegalArgumentException("CashFlow shouldn't be null");
        }
        else if (!validateCashFlow(cashFlow))
        {
            throw new IllegalArgumentException("CashFlow date is less than current MonthlySurplus date");
        }
        this.surplus+=cashFlow.getAmount();
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
            return oneOff.getDateStart().toLocalDate().isEqual(LocalDateTime.now().toLocalDate());
        }
    }
}
