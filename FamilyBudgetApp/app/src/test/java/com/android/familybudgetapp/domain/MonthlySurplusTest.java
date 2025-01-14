package com.android.familybudgetapp.domain;

import static org.junit.Assert.*;

import android.util.Pair;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.YearMonth;

public class MonthlySurplusTest
{

    private  MonthlySurplus surplus;
    private LocalDateTime date= YearMonth.now().atEndOfMonth().atStartOfDay();
    private YearMonth yearMonth=YearMonth.now();

    @Before
    public void setUp()
    {
        surplus=new MonthlySurplus(YearMonth.now());
    }


    @Test
    public void instantiateObjectWithInvalidArguments()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            new MonthlySurplus(null);
        });
    }

    @Test
    public void getDate()
    {
        assertEquals(date,surplus.getDate());
    }

    @Test
    public void getSurplus()
    {
        CashFlow cashFlow=new OneOff(10,new Income("test"),date.plusMonths(1));
        surplus.addCashFlowToSurplus(cashFlow);

        assertEquals(0,surplus.getSurplus());

        cashFlow=new OneOff(10,new Income("test"),yearMonth.atDay(10).atStartOfDay());
        surplus.addCashFlowToSurplus(cashFlow);
        assertEquals(cashFlow.getAmount(),surplus.getSurplus());
    }

    @Test
    public void setDate()
    {
        YearMonth current= yearMonth.plusMonths(1);
        surplus.setDate(current);
        LocalDateTime datetime=surplus.getDate();
        assertEquals(current,YearMonth.of(datetime.getYear(),datetime.getMonth()));
    }

    @Test
    public void addValidCashFlowToSurplus()
    {
        int amount=10;
        CashFlow cashFlow1=new Repeating(amount,new Income("test"),date,date.plusMonths(5),recurPeriod.Daily);
        CashFlow cashFlow2 =new OneOff(amount,new Income("test"),date.plusMonths(1));
        surplus.addCashFlowToSurplus(cashFlow1);
        surplus.addCashFlowToSurplus(cashFlow2);

        assertEquals(amount,surplus.getSurplus());
    }

    @Test
    public void addAllowanceMoneyBoxPairNullTest()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            surplus.addAllowanceMoneyBoxPair(null, "reason");
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            surplus.addAllowanceMoneyBoxPair(new Allowance(15, date), null);
        });

    }


   @Test
    public void addInvalidCashFlowToSurplus()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            surplus.addCashFlowToSurplus(null);
        });
    }

    @Test
    public void validateValidCashFlow()
    {
        CashFlow repeating=new Repeating(10,new Income("test"),date,date.plusMonths(1),recurPeriod.Monthly);
        CashFlow oneOff=new OneOff(10,new Income("test"),date.plusMonths(1));

        assertTrue(surplus.validateCashFlow(repeating));
        assertTrue(surplus.validateCashFlow(oneOff));
    }


    @Test
    public void validateInvalidCashFlow()
    {
        Repeating wrongEndDateRepeating=new  Repeating(10,new Income("test"),date,date.plusDays(1),recurPeriod.Monthly);
        CashFlow wrongDateOneOff = new OneOff(10,new Income("test"),date);
        // Simulate passage of time
        wrongEndDateRepeating.DebugSetDateStart(date.minusMonths(5));
        wrongEndDateRepeating.DebugSetDateEnd(date.minusMonths(1));
        wrongDateOneOff.DebugSetDateStart(date.minusMonths(1));

        assertFalse(surplus.validateCashFlow(wrongEndDateRepeating));
        assertFalse(surplus.validateCashFlow(wrongDateOneOff));
    }

    @Test
    public void removeNullCashFlowFromSurplus()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            surplus.removeCashFlowFromSurplus(null);
        });
    }

    @Test
    public void removeValidCashFlowFromSurplus()
    {

        CashFlow cashFlow=new Repeating(10,new Income("test"),date,date.plusMonths(5),recurPeriod.Daily);
        surplus.addCashFlowToSurplus(cashFlow);
        surplus.removeCashFlowFromSurplus(cashFlow);
        assertEquals(0,surplus.getSurplus());
    }
}