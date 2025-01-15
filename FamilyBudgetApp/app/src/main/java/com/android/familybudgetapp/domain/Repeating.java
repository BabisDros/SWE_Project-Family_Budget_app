package com.android.familybudgetapp.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

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
    @Override
    public int getMonthlyAmount(YearMonth yearMonth) {
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        LocalDate overlapStart = getDateStart().isAfter(startOfMonth.atStartOfDay()) ? getDateStart().toLocalDate() : startOfMonth;
        LocalDate overlapEnd = getDateEnd().isBefore(endOfMonth.atStartOfDay()) ? getDateEnd().toLocalDate() : endOfMonth;

        // If dateStart is after the month or dateEnd is before the month the overlap is 0
        if (overlapStart.isAfter(overlapEnd)) {
            return 0;
        }

        int overlappingDays = (int) (overlapEnd.toEpochDay() - overlapStart.toEpochDay() + 1);
        int daysOfCurMonth = yearMonth.lengthOfMonth();
        switch (recurrencePeriod) {
            case Daily:
                return getAmount() * overlappingDays;

            case Weekly:
                // The full amount is added if there's any overlap
                int overlappingWeeks = (overlappingDays+6) / 7; // add 6 to round up
                return getAmount() * overlappingWeeks;

            case Monthly:
                // The full amount is added if there's any overlap
                return getAmount();

            case Yearly:
                // return the full yearly amount on the month of the start date
                if (yearMonth.getMonth() == YearMonth.from(getDateStart()).getMonth()) {
                    return getAmount();
                }
                return 0;


            default:
                throw new IllegalArgumentException("Invalid recurrence period");
        }
    }

    // Calculates and returns the yearly amount based on the recurrence period
    @Override
    public int getYearlyAmount(YearMonth yearMonth) {
        LocalDate startOfYear = yearMonth.atDay(1).withDayOfYear(1);
        LocalDate endOfYear = yearMonth.atDay(1).withDayOfYear(startOfYear.lengthOfYear());

        LocalDate overlapStart = getDateStart().isAfter(startOfYear.atStartOfDay()) ? getDateStart().toLocalDate() : startOfYear;
        LocalDate overlapEnd = getDateEnd().isBefore(endOfYear.atStartOfDay()) ? getDateEnd().toLocalDate() : endOfYear;

        // If dateStart is after the year or dateEnd is before the year, the overlap is 0
        if (overlapStart.isAfter(overlapEnd)) {
            return 0;
        }

        int overlappingDays = (int) (overlapEnd.toEpochDay() - overlapStart.toEpochDay() + 1);
        switch (recurrencePeriod) {
            case Daily:
                return getAmount() * overlappingDays;

            case Weekly:
                // The full amount is added for partial weeks
                int overlappingWeeks = (overlappingDays + 6) / 7; // add 6 to round up
                return getAmount() * overlappingWeeks;

            case Monthly:
                YearMonth startMonth = YearMonth.from(overlapStart);
                YearMonth endMonth = YearMonth.from(overlapEnd);
                int overlappingMonths = (endMonth.getYear() - startMonth.getYear()) * 12 + endMonth.getMonthValue() - startMonth.getMonthValue() + 1;
                return getAmount() * overlappingMonths;

            case Yearly:
                // The full amount is added if there's any overlap
                return getAmount();

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