package com.android.familybudgetapp.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.time.YearMonth;

public class Repeating extends CashFlow {
    private LocalDateTime dateEnd;
    private recurPeriod recurrencePeriod;
    // Getters
    public LocalDateTime getDateEnd() {
        return dateEnd;
    }
    public recurPeriod getRecurrencePeriod() {
        return recurrencePeriod;
    }

    public Repeating(int amount, CashFlowCategory category, LocalDateTime dateStart, LocalDateTime dateEnd, recurPeriod recurrencePeriod) {
        super(amount, category, dateStart);
        this.dateEnd = dateEnd;
        this.recurrencePeriod = recurrencePeriod;
    }

    // Setters
    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }
    public void setRecurrencePeriod(recurPeriod recurrencePeriod) {
        this.recurrencePeriod = recurrencePeriod;
    }
    @Override
    public String toString() {
        return "Repeating{" +
                "dateEnd=" + dateEnd +
                ", recurrencePeriod=" + recurrencePeriod +
                '}';
    }
    // Calculates and returns the monthly amount based on the recurrence period
    public int getMonthlyAmount() {
        int daysOfCurMonth = YearMonth.now().lengthOfMonth();
        switch (recurrencePeriod) {
            case Daily:
                return getAmount() * daysOfCurMonth;
            case Weekly:
                return getAmount() * (daysOfCurMonth/7);
            case Monthly:
                return getAmount();
            case Yearly:
                return getAmount() / 12;
            default:
                throw new IllegalArgumentException("Invalid recurrence period");
        }
    }
}