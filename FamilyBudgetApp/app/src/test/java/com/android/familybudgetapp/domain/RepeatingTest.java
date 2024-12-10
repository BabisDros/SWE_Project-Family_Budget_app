package com.android.familybudgetapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;

public class RepeatingTest {

    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private CashFlowCategory incomeCategory;
    private CashFlowCategory expenseCategory;

    @Before
    public void setUp() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 20, 0, 0);
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
        LocalDateTime newDateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+2, Month.JANUARY, 1, 0, 0);
        repeating.setDateEnd(newDateEnd);
        assertEquals(newDateEnd, repeating.getDateEnd());
    }
    @Test
    public void testSetDateEnd_InvalidDate() {
        Repeating repeating = new Repeating(1000, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        LocalDateTime invalidDateEnd = LocalDateTime.of(LocalDateTime.now().getYear()-1, Month.JANUARY, 1, 0, 0);
        assertThrows(IllegalArgumentException.class, () -> {repeating.setDateEnd(invalidDateEnd);});
    }

    @Test
    public void testSetDateEnd_nullDate() {
        Repeating repeating = new Repeating(1000, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        LocalDateTime invalidDateEnd = null;
        assertThrows(IllegalArgumentException.class, () -> {repeating.setDateEnd(invalidDateEnd);});
    }

    @Test
    public void testSetDateEnd_invalidDateBeforeStart() {
        Repeating repeating = new Repeating(1000, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        LocalDateTime invalidDateEnd = LocalDateTime.of(2023, Month.DECEMBER, 31, 0, 0);
        assertThrows(IllegalArgumentException.class, () -> {repeating.setDateEnd(invalidDateEnd);});
    }

    @Test
    public void testGetMonthlyAmount_DailyRecurrence_Ongoing() {
        Repeating repeating = new Repeating(10, incomeCategory, dateStart, dateEnd, recurPeriod.Daily);
        int daysOfCurMonth = YearMonth.now().lengthOfMonth();
        assertEquals(repeating.getMonthlyAmount(), repeating.getAmount() * daysOfCurMonth);
    }

    @Test
    public void testGetMonthlyAmount_DailyRecurrence_FinalMonth() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth()+2, 0, 0);
        Repeating repeating = new Repeating(10, incomeCategory, dateStart, dateEnd, recurPeriod.Daily);
        int endDay = dateEnd.getDayOfMonth();
        assertEquals(  repeating.getMonthlyAmount(), repeating.getAmount() * endDay);
    }

    @Test
    public void testGetMonthlyAmount_WeeklyRecurrence_Ongoing() {
        Repeating repeating = new Repeating(100, expenseCategory, dateStart, dateEnd, recurPeriod.Weekly);
        int daysOfCurMonth = YearMonth.now().lengthOfMonth();
        assertEquals(repeating.getAmount() * (daysOfCurMonth/7.0), repeating.getMonthlyAmount());
    }

    @Test
    public void testGetMonthlyAmount_WeeklyRecurrence_FinalMonth() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth()+2, 0, 0);
        Repeating repeating = new Repeating(100, expenseCategory, dateStart, dateEnd, recurPeriod.Weekly);
        int endDay = dateEnd.getDayOfMonth();
        double remainingWeeks = endDay / 7.0;
        assertEquals(repeating.getAmount()*remainingWeeks, repeating.getMonthlyAmount());
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
    public void testGetMonthlyInvalidRecur() {
        Repeating repeating = new Repeating(12000, incomeCategory, dateStart, dateEnd, recurPeriod.testcase);
        assertThrows(IllegalArgumentException.class, () -> {repeating.getMonthlyAmount();});
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

    @Test
    public void testToString() {
        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        String expected = "Repeating{dateEnd=" + (LocalDateTime.now().getYear()+1) + "-12-20T00:00, recurrencePeriod=Monthly}";
        assertEquals(expected, repeating.toString());
    }

    @Test
    public void negativeAmount() {
        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        assertThrows(IllegalArgumentException.class, () -> {repeating.setAmount(-50);});
    }

    @Test
    public void nullCategory() {
        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        assertThrows(IllegalArgumentException.class, () -> {repeating.setCategory(null);});
    }

    @Test
    public void nullDateStart() {
        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        assertThrows(IllegalArgumentException.class, () -> {repeating.setCategory(null);});
    }

    @Test
    public void dateStartBeforeNow() {
        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        assertThrows(IllegalArgumentException.class, () -> {repeating.setDateStart(LocalDateTime.now().minusMonths(2));});
    }

}
