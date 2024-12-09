package com.android.familybudgetapp.domain;

import java.time.LocalDateTime;
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
        if (dateEnd.isBefore(LocalDateTime.now())) {
            return 0;
        }

        int daysOfCurMonth = YearMonth.now().lengthOfMonth();
        int endDay = dateEnd.getDayOfMonth();
        int endMonth = dateEnd.getMonthValue();
        int endYear = dateEnd.getYear();
        switch (recurrencePeriod) {
            case Daily:
                // check if dateEnd is this month, return partial
                if (LocalDateTime.now().getMonthValue() == endMonth &&
                        LocalDateTime.now().getYear() == endYear) {
                    return getAmount() * endDay;
                }
                // ongoing
                return getAmount() * daysOfCurMonth;
            case Weekly:
                if (LocalDateTime.now().getMonthValue() == endMonth &&
                        LocalDateTime.now().getYear() == endYear) {
                    int remainingWeeks = (int) Math.ceil(endDay / 7.0);
                    return getAmount() * remainingWeeks;
                }
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