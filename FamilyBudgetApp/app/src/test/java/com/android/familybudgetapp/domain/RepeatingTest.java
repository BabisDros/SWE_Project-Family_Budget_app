package com.android.familybudgetapp.domain;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.YearMonth;
public class RepeatingTest {

    @Test
    public void testConstructorAndGetters() {
        int amount = 100;
        CashFlowCategory category = new Income("test");
        LocalDateTime dateStart = LocalDateTime.now();
        LocalDateTime dateEnd = dateStart.plusMonths(1);
        recurPeriod recurrencePeriod = recurPeriod.Monthly;

        Repeating repeating = new Repeating(amount, category, dateStart, dateEnd, recurrencePeriod);

        assertEquals(amount, repeating.getAmount());
        assertEquals(category, repeating.getCategory());
        assertEquals(dateStart, repeating.getDateStart());
        assertEquals(dateEnd, repeating.getDateEnd());
        assertEquals(recurrencePeriod, repeating.getRecurrencePeriod());
    }

    @Test
    public void testSetters() {
        Repeating repeating = new Repeating(100, new Income("test"), LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        LocalDateTime newDateEnd = LocalDateTime.now().plusMonths(2);
        recurPeriod newRecurrencePeriod = recurPeriod.Weekly;

        repeating.setDateEnd(newDateEnd);
        repeating.setRecurrencePeriod(newRecurrencePeriod);

        assertEquals(newDateEnd, repeating.getDateEnd());
        assertEquals(newRecurrencePeriod, repeating.getRecurrencePeriod());
    }

    @Test
    public void testToString() {
        LocalDateTime dateStart = LocalDateTime.now();
        LocalDateTime dateEnd = LocalDateTime.now().plusMonths(1);
        Repeating repeating = new Repeating(100, new Income("test"), dateStart, dateEnd, recurPeriod.Monthly);

        String toStringResult = repeating.toString();

        assertTrue(toStringResult.contains("dateEnd="));
        assertTrue(toStringResult.contains("recurrencePeriod=Monthly"));
    }

    @Test
    public void testGetMonthlyAmount() {
        int dailyAmount = 10;
        int weeklyAmount = 70;
        int monthlyAmount = 300;
        int yearlyAmount = 3600;
        YearMonth currentMonth = YearMonth.now();
        int daysOfCurMonth = currentMonth.lengthOfMonth();

        // Daily
        Repeating daily = new Repeating(dailyAmount, new Income("test"), LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Daily);
        assertEquals(dailyAmount * daysOfCurMonth, daily.getMonthlyAmount());

        // Weekly
        Repeating weekly = new Repeating(weeklyAmount, new Income("test"), LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Weekly);
        assertEquals(weeklyAmount * (daysOfCurMonth / 7), weekly.getMonthlyAmount());

        // Monthly
        Repeating monthly = new Repeating(monthlyAmount, new Income("test"), LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        assertEquals(monthlyAmount, monthly.getMonthlyAmount());

        // Yearly
        Repeating yearly = new Repeating(yearlyAmount, new Income("test"), LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Yearly);
        assertEquals(yearlyAmount / 12, yearly.getMonthlyAmount());
    }

    @Test
    public void testGetMonthlyAmountFinalMonthDaily() {
        int dailyAmount = 10;
        LocalDateTime now = LocalDateTime.now();
        int endDay = 15; // end mid-month
        LocalDateTime dateEnd = LocalDateTime.of(now.getYear(), now.getMonthValue(), endDay, 0, 0);

        Repeating daily = new Repeating(dailyAmount, new Income("test"), now.minusMonths(1), dateEnd, recurPeriod.Daily);

        assertEquals(dailyAmount * endDay, daily.getMonthlyAmount());
    }

    @Test
    public void testGetMonthlyAmountFinalMonthWeekly() {
        int weeklyAmount = 70;
        LocalDateTime now = LocalDateTime.now();
        int endDay = 15;
        LocalDateTime dateEnd = LocalDateTime.of(now.getYear(), now.getMonthValue(), endDay, 0, 0);

        Repeating weekly = new Repeating(weeklyAmount, new Income("test"), now.minusMonths(1), dateEnd, recurPeriod.Weekly);

        int remainingWeeks = (int) Math.ceil(endDay / 7.0);
        assertEquals(weeklyAmount * remainingWeeks, weekly.getMonthlyAmount());
    }

    @Test
    public void testGetMonthlyAmountRecurrenceEnded() {
        LocalDateTime dateEnd = LocalDateTime.now().minusMonths(1);
        Repeating repeating = new Repeating(100, new Income("test"), LocalDateTime.now().minusMonths(2), dateEnd, recurPeriod.Daily);

        assertEquals(0, repeating.getMonthlyAmount());
    }
}
