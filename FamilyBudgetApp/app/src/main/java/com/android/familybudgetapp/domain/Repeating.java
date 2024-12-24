package com.android.familybudgetapp.domain;

import java.time.LocalDate;
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
        setDateEnd(dateEnd);
        setRecurrencePeriod(recurrencePeriod);
    }

    // Setters
    public void setDateEnd(LocalDateTime dateEnd) {
        if (!validateDateEnd(getDateStart(), dateEnd))
            throw new IllegalArgumentException("dateEnd is invalid.");
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
                    int remainingWeeks = endDay / 7;
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

    public static boolean validateDateEnd(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return dateEnd != null &&
                !YearMonth.from(dateEnd).isBefore(YearMonth.now()) &&
                !dateEnd.isBefore(dateStart);
    }

    /**
     * Used ONLY to assist Junit tests with time Simulation
     */
    public void DebugSetDateEnd(LocalDateTime date)
    {
        this.dateEnd = date;
    }
}