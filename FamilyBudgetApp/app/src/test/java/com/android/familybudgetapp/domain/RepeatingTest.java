package com.android.familybudgetapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;

public class RepeatingTest {

    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private CashFlowCategory incomeCategory;
    private CashFlowCategory expenseCategory;

    @Before
    public void setUp() {
        dateStart = LocalDateTime.of(2024, Month.DECEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(2024, Month.DECEMBER, 20, 0, 0);
        incomeCategory = new Income("Salary");
        expenseCategory = new Expense("Rent", 900);
    }

    @Test
    public void testConstructor() {
        Repeating repeating = new Repeating(1000, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        assertEquals(1000, repeating.getAmount());
        assertEquals(incomeCategory, repeating.getCategory());
        assertEquals(dateStart, repeating.getDateStart());
        assertEquals(dateEnd, repeating.getDateEnd());
        assertEquals(recurPeriod.Monthly, repeating.getRecurrencePeriod());
    }

    @Test
    public void testSetDateEnd_validDate() {
        Repeating repeating = new Repeating(1000, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        LocalDateTime newDateEnd = LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0);
        repeating.setDateEnd(newDateEnd);
        assertEquals(newDateEnd, repeating.getDateEnd());
    }

    @Test
    public void testSetDateEnd_invalidDateBeforeStart() {
        Repeating repeating = new Repeating(1000, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        LocalDateTime invalidDateEnd = LocalDateTime.of(2023, Month.DECEMBER, 31, 0, 0);
        assertThrows(IllegalArgumentException.class, () -> {repeating.setDateEnd(invalidDateEnd);});
    }

    @Test
    public void testGetMonthlyAmount_DailyRecurrence() {
        Repeating repeating = new Repeating(10, incomeCategory, dateStart, dateEnd, recurPeriod.Daily);

        double monthlyAmount = repeating.getMonthlyAmount();
        assertTrue(monthlyAmount > 0);
    }

    @Test
    public void testGetMonthlyAmount_WeeklyRecurrence() {
        Repeating repeating = new Repeating(100, expenseCategory, dateStart, dateEnd, recurPeriod.Weekly);

        double monthlyAmount = repeating.getMonthlyAmount();
        assertTrue(monthlyAmount > 0);
    }

    @Test
    public void testGetMonthlyAmount_MonthlyRecurrence() {
        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);

        double monthlyAmount = repeating.getMonthlyAmount();
        assertEquals(500, monthlyAmount);
    }

    @Test
    public void testGetMonthlyAmount_YearlyRecurrence() {
        Repeating repeating = new Repeating(12000, incomeCategory, dateStart, dateEnd, recurPeriod.Yearly);

        double monthlyAmount = repeating.getMonthlyAmount();
        assertEquals(1000, monthlyAmount);
    }

    @Test
    public void testSetRecurrencePeriod() {
        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        repeating.setRecurrencePeriod(recurPeriod.Weekly);
        assertEquals(recurPeriod.Weekly, repeating.getRecurrencePeriod());
    }

    @Test
    public void testDebugMethods()
    {
        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        repeating.DebugSetDateStart(dateStart.minusMonths(5));
        repeating.DebugSetDateEnd(dateEnd.minusMonths(4));
        assertEquals(dateStart.minusMonths(5), repeating.getDateStart());
        assertEquals(dateEnd.minusMonths(4), repeating.getDateEnd());
    }



}
