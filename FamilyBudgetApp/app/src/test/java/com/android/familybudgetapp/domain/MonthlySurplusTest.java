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
        CashFlow cashFlow=new Repeating(10,new Income("test"),date.minusMonths(5),date.minusMonths(1),recurPeriod.Monthly);
        CashFlow oneOff=new OneOff(10,new Income("test"),date);

        assertThrows(IllegalArgumentException.class, ()-> {
            surplus.addCashFlowToSurplus(null);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            surplus.addCashFlowToSurplus(cashFlow);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            surplus.addCashFlowToSurplus(oneOff);
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
        CashFlow wrongEndDateRepeating=new  Repeating(10,new Income("test"),date.minusMonths(5),date.minusMonths(1),recurPeriod.Monthly);
        CashFlow wrongDateOneOff=new  Repeating(10,new Income("test"),date.minusMonths(5),date.minusMonths(1),recurPeriod.Monthly);

        assertFalse(surplus.validateCashFlow(wrongEndDateRepeating));
        assertFalse(surplus.validateCashFlow(wrongDateOneOff));
    }
}