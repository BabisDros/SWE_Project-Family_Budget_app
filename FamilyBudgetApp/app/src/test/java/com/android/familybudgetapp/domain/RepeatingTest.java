package com.android.familybudgetapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

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
    // dateStart and dateEnd within month of interest
    public void testGetMonthlyAmountDaily() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 20, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Daily);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        int expectedAmount = 500 * ((int) ChronoUnit.DAYS.between(dateStart.toLocalDate(), dateEnd.toLocalDate()) + 1);
        assertEquals(expectedAmount, repeating.getMonthlyAmount(yearMonth));
    }

    @Test
    // dateStart within month of interest, dateEnd after month of interest
    public void testGetMonthlyAmountDaily_endAfter() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.NOVEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 20, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Daily);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.NOVEMBER);

        int expectedAmount = 500 * 16; // 16 days in November inclusive (15-30)
        assertEquals(expectedAmount, repeating.getMonthlyAmount(yearMonth));
    }

    @Test
    // dateStart before month of interest and dateEnd within month of interest
    public void testGetMonthlyAmountDaily_StartBefore() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.NOVEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 20, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Daily);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        int expectedAmount = 500 * 20;
        assertEquals(expectedAmount, repeating.getMonthlyAmount(yearMonth));
    }

    @Test
    // month of interest not within the range of dateStart and dateEnd
    public void testGetMonthlyAmountDaily_OutOfRange() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 20, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Daily);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.NOVEMBER);

        int expectedAmount = 0;
        assertEquals(expectedAmount, repeating.getMonthlyAmount(yearMonth));
    }

    @Test
    // illegal recurrence period
    public void testGetMonthlyAmountDaily_IllegalRecurrence() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 20, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.testcase);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        assertThrows(IllegalArgumentException.class, () -> {repeating.getMonthlyAmount(yearMonth);});
    }

    @Test
    public void testGetMonthlyAmountWeekly() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 20, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Weekly);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        int expectedAmount = 500;
        assertEquals(expectedAmount, repeating.getMonthlyAmount(yearMonth));
    }

    @Test
    public void testGetMonthlyAmountMonthly() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 20, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        int expectedAmount = 500;
        assertEquals(expectedAmount, repeating.getMonthlyAmount(yearMonth));
    }

    @Test
    public void testGetMonthlyAmountYearly() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 1, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+2, Month.DECEMBER, 1, 0, 0);

        Repeating repeating = new Repeating(100, incomeCategory, dateStart, dateEnd, recurPeriod.Yearly);

        assertEquals(100, repeating.getMonthlyAmount(YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER)));
        assertEquals(0, repeating.getMonthlyAmount(YearMonth.of(LocalDateTime.now().getYear()+2, Month.JANUARY)));
        assertEquals(100, repeating.getMonthlyAmount(YearMonth.of(LocalDateTime.now().getYear()+2, Month.DECEMBER)));
        assertEquals(0, repeating.getMonthlyAmount(YearMonth.of(LocalDateTime.now().getYear()+3, Month.DECEMBER)));
    }

    @Test
    public void testGetYearlyAmountDaily() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 20, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Daily);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        int expectedAmount = 500 * ((int) ChronoUnit.DAYS.between(dateStart.toLocalDate(), dateEnd.toLocalDate()) + 1);
        assertEquals(expectedAmount, repeating.getYearlyAmount(yearMonth));
    }


    @Test
    public void testGetYearlyAmountWeekly() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 15, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 20, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Weekly);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        int expectedAmount = 500 * 1;
        assertEquals(expectedAmount, repeating.getYearlyAmount(yearMonth));
    }

    @Test
    public void testGetYearlyAmountMonthly() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.AUGUST, 1, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.OCTOBER, 1, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Monthly);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        int expectedAmount = 500 * 3;
        assertEquals(expectedAmount, repeating.getYearlyAmount(yearMonth));
    }

    @Test
    public void testGetYearlyAmountYearly() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.OCTOBER, 1, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.OCTOBER, 1, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Yearly);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        int expectedAmount = 500;
        assertEquals(expectedAmount, repeating.getYearlyAmount(yearMonth));
    }

    @Test
    // startDate before year of interest and endDate within year of interest
    public void testGetYearlyAmountYearly_startBefore() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 1, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+2, Month.DECEMBER, 1, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Yearly);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+2, Month.DECEMBER);

        int expectedAmount = 500;
        assertEquals(expectedAmount, repeating.getYearlyAmount(yearMonth));
    }

    @Test
    // startDate within year of interest and endDate after year of interest
    public void testGetYearlyAmountYearly_endAfter() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 1, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+2, Month.DECEMBER, 1, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Yearly);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        int expectedAmount = 500;
        assertEquals(expectedAmount, repeating.getYearlyAmount(yearMonth));
    }

    @Test
    // startDate and endDate out of range of year of interest
    public void testGetYearlyAmountYearly_outOfRange() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 1, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+2, Month.DECEMBER, 1, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.Yearly);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+3, Month.DECEMBER);

        int expectedAmount = 0;
        assertEquals(expectedAmount, repeating.getYearlyAmount(yearMonth));
    }

    @Test
    // illegal recurrence period
    public void testGetYearlyAmountYearly_IllegalRecurrence() {
        dateStart = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.DECEMBER, 1, 0, 0);
        dateEnd = LocalDateTime.of(LocalDateTime.now().getYear()+2, Month.DECEMBER, 1, 0, 0);

        Repeating repeating = new Repeating(500, incomeCategory, dateStart, dateEnd, recurPeriod.testcase);
        YearMonth yearMonth = YearMonth.of(LocalDateTime.now().getYear()+1, Month.DECEMBER);

        assertThrows(IllegalArgumentException.class, () -> {repeating.getYearlyAmount(yearMonth);});
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

    @Test
    public void OneOffMonthly()
    {
        OneOff oneOff = new OneOff(500, incomeCategory, dateStart);
        assertEquals(500, oneOff.getMonthlyAmount());
    }

}
