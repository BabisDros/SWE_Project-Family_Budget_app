package com.android.familybudgetapp.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class MonthlySurplusTest
{

    private  MonthlySurplus surplus;
    private LocalDateTime date=LocalDateTime.now();

    @Before
    public void setUp()
    {
        surplus=new MonthlySurplus(date);
    }


    @Test
    public void instantiateObjectWithInvalidArguments()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            new MonthlySurplus(null);;
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

        assertEquals(cashFlow.getAmount(),surplus.getSurplus());
    }

    @Test
    public void setDate()
    {
        LocalDateTime newDate=date.plusMonths(1);
        surplus.setDate(newDate);
        assertEquals(newDate,surplus.getDate());
    }

    @Test
    public void addValidCashFlowToSurplus()
    {
        int amount=10;
        CashFlow cashFlow1=new Repeating(amount,new Income("test"),date,date.plusMonths(5),recurPeriod.Daily);
        CashFlow cashFlow2 =new OneOff(amount,new Income("test"),date.plusMonths(1));
        surplus.addCashFlowToSurplus(cashFlow1);
        surplus.addCashFlowToSurplus(cashFlow2);

        assertEquals(amount*2,surplus.getSurplus());
    }


   @Test
    public void addInvalidCashFlowToSurplus()
    {
        Repeating expiredEndDateRepeating=new  Repeating(10,new Income("test"),date,date.plusDays(1),recurPeriod.Monthly);
        OneOff expiredDateOneOff = new OneOff(10,new Income("test"),date);
        // Simulate passage of time
        expiredEndDateRepeating.DebugSetDateStart(date.minusMonths(5));
        expiredEndDateRepeating.DebugSetDateEnd(date.minusMonths(1));
        expiredDateOneOff.DebugSetDateStart(date.minusMonths(1));

        assertThrows(IllegalArgumentException.class, ()-> {
            surplus.addCashFlowToSurplus(null);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            surplus.addCashFlowToSurplus(expiredEndDateRepeating);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            surplus.addCashFlowToSurplus(expiredDateOneOff);
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