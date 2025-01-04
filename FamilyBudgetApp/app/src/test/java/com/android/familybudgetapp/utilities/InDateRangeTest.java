package com.android.familybudgetapp.utilities;

import static org.junit.Assert.*;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.recurPeriod;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class InDateRangeTest {

    private CashFlow good1;
    private CashFlow good2;
    private CashFlow bad1;
    private CashFlow bad2;
    private CashFlowCategory category;
    private LocalDateTime localDateTime;
    @Before
    public void setUp(){
        localDateTime = LocalDateTime.now();
        category = new Income("Job");
        good1 = new OneOff(100, category, localDateTime);
        good2 = new Repeating(100, category, localDateTime, localDateTime.plusMonths(1), recurPeriod.Monthly);
        bad1 = new OneOff(100, category, localDateTime.plusMonths(1).plusYears(1));
        bad2 = new Repeating(100, category, localDateTime.plusYears(1), localDateTime.plusYears(2), recurPeriod.Monthly);
    }

    @Test
    public void cashFlowInMonthlyRange() {
        assertTrue(InDateRange.cashFlowInMonthlyRange(good1));
        assertTrue(InDateRange.cashFlowInMonthlyRange(good2));
        assertFalse(InDateRange.cashFlowInMonthlyRange(bad1));
        assertFalse(InDateRange.cashFlowInMonthlyRange(bad2));
    }

    @Test
    public void cashFlowInYearlyRange() {
        assertTrue(InDateRange.cashFlowInYearlyRange(good1));
        assertTrue(InDateRange.cashFlowInYearlyRange(good2));
        assertFalse(InDateRange.cashFlowInYearlyRange(bad1));
        assertFalse(InDateRange.cashFlowInYearlyRange(bad2));
    }
}