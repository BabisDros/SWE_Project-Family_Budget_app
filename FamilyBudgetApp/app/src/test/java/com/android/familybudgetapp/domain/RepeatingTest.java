package com.android.familybudgetapp.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

class RepeatingTest {

    @Test
    void testConstructorAndGetters() {
        int amount = 100;
        CashFlowCategory category = new Income("test");
        LocalDateTime dateStart = LocalDateTime.now();
        LocalDateTime dateEnd = dateStart.plusMonths(100);
        recurPeriod recurrencePeriod = recurPeriod.Monthly;

        Repeating repeating = new Repeating(amount, category, dateStart, dateEnd, recurrencePeriod);

        assertEquals(amount, repeating.getAmount());
        assertEquals(category, repeating.getCategory());
        assertEquals(dateStart, repeating.getDateStart());
        assertEquals(dateEnd, repeating.getDateEnd());
        assertEquals(recurrencePeriod, repeating.getRecurrencePeriod());
    }

    @Test
    void testSetters() {
        Repeating repeating = new Repeating(100, new Income("test"), LocalDateTime.now(), LocalDateTime.now(), recurPeriod.Monthly);
        LocalDateTime newDateEnd = LocalDateTime.now().plusMonths(10);
        recurPeriod newRecurrencePeriod = recurPeriod.Weekly;

        repeating.setDateEnd(newDateEnd);
        repeating.setRecurrencePeriod(newRecurrencePeriod);

        assertEquals(newDateEnd, repeating.getDateEnd());
        assertEquals(newRecurrencePeriod, repeating.getRecurrencePeriod());
    }

    @Test
    void testToString() {
        LocalDateTime dateStart = LocalDateTime.now().plusMonths(10);
        LocalDateTime dateEnd = dateStart.plusMonths(10);
        Repeating repeating = new Repeating(100, new Income("test"), dateStart, dateEnd, recurPeriod.Monthly);

        String toStringResult = repeating.toString();

        assertTrue(toStringResult.contains("dateEnd="));
        assertTrue(toStringResult.contains("recurrencePeriod=Monthly"));
    }

    @Test
    void testGetMonthlyAmount() {
        int dailyAmount = 10;
        int weeklyAmount = 70;
        int monthlyAmount = 300;
        int yearlyAmount = 3600;
        YearMonth currentMonth = YearMonth.now();
        int daysOfCurMonth = currentMonth.lengthOfMonth();

        // Daily
        Repeating daily = new Repeating(dailyAmount, new Income("test"), LocalDateTime.now(), LocalDateTime.now(), recurPeriod.Daily);
        assertEquals(dailyAmount * daysOfCurMonth, daily.getMonthlyAmount());

        // Weekly
        Repeating weekly = new Repeating(weeklyAmount, new Income("test"), LocalDateTime.now(), LocalDateTime.now(), recurPeriod.Weekly);
        assertEquals(weeklyAmount * (daysOfCurMonth / 7), weekly.getMonthlyAmount());

        // Monthly
        Repeating monthly = new Repeating(monthlyAmount, new Income("test"), LocalDateTime.now(), LocalDateTime.now(), recurPeriod.Monthly);
        assertEquals(monthlyAmount, monthly.getMonthlyAmount());

        // Yearly
        Repeating yearly = new Repeating(yearlyAmount, new Income("test"), LocalDateTime.now(), LocalDateTime.now(), recurPeriod.Yearly);
        assertEquals(yearlyAmount / 12, yearly.getMonthlyAmount());
    }

    @Test
    void testGetMonthlyAmountInvalidRecurrence() {
        Repeating invalid = new Repeating(100, new Income("test"), LocalDateTime.now(), LocalDateTime.now(), null);
        assertThrows(IllegalArgumentException.class, invalid::getMonthlyAmount);
    }
}
