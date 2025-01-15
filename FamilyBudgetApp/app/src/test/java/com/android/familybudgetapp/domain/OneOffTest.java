package com.android.familybudgetapp.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
public class OneOffTest {

    @Test
    public void testSettersGetters() {
        Income incomeCategory = new Income("Salary");
        OneOff oneOff = new OneOff(100, incomeCategory, LocalDateTime.of(
                YearMonth.now().getYear()+1, Month.JANUARY, 1, 0, 0));

        oneOff.setAmount(200);
        assertEquals(200, oneOff.getAmount());
        assertEquals(200, oneOff.getMonthlyAmount());
        assertEquals(200, oneOff.getYearlyAmount());

        Income bonus = new Income("Bonus");
        oneOff.setCategory(bonus);
        assertEquals(bonus, oneOff.getCategory());
        assertThrows(IllegalArgumentException.class, () -> oneOff.setCategory(null));

        assertThrows(IllegalArgumentException.class, () -> oneOff.setAmount(-10));

        oneOff.setDateStart(LocalDateTime.of(YearMonth.now().getYear()+1, Month.JANUARY, 2, 0, 0));
        assertEquals(LocalDateTime.of(YearMonth.now().getYear()+1, Month.JANUARY, 2, 0, 0), oneOff.getDateStart());
        LocalDateTime beforeNow = LocalDateTime.of(YearMonth.now().getYear()-1, Month.JANUARY, 2, 0, 0);
        assertThrows(IllegalArgumentException.class, () -> oneOff.setDateStart(beforeNow));
        assertThrows(IllegalArgumentException.class, () -> oneOff.setDateStart(null));
    }

    @Test
    public void testToString() {
        /*
        Original toString:
                return "CashFlow{" +
                "amount=" + amount +
                ", category=" + category.toString() +
                ", dateStart=" + dateStart +
                '}';
         */
        Income incomeCategory = new Income("Salary");
        OneOff oneOff = new OneOff(100, incomeCategory, LocalDateTime.of(
                2030, Month.JANUARY, 1, 0, 0));
        assertEquals("OneOff{amount=100, category=Income{name='Salary'}, dateStart=2030-01-01T00:00}", oneOff.toString());
    }

    @Test
    public void testNonOverloadedGetMonthlyYearly()
    {
        Income incomeCategory = new Income("Salary");
        OneOff oneOff = new OneOff(100, incomeCategory, LocalDateTime.of(
                2030, Month.JANUARY, 1, 0, 0));
        assertEquals(100, oneOff.getMonthlyAmount(YearMonth.of(2030, Month.JANUARY)));
        assertEquals(100, oneOff.getYearlyAmount(YearMonth.of(2030, Month.JANUARY)));
        assertEquals(0, oneOff.getMonthlyAmount(YearMonth.of(2030, Month.FEBRUARY)));
        assertEquals(0, oneOff.getYearlyAmount(YearMonth.of(2031, Month.JANUARY)));
    }

}
