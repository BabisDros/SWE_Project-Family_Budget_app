package com.android.familybudgetapp.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;


public class AllowanceTest {

    public Allowance tester;
    public LocalDateTime dateTester;
    @Before
    public void setUp() {
        dateTester = LocalDateTime.of(2024, 10, 28, 6, 30);
        tester = new Allowance(100, dateTester);
    }

    @Test
    public void getAmount() {
        assertEquals(100, tester.getAmount());
    }

    @Test
    public void InitiateNegative() {
        tester = new Allowance(-10, dateTester);
        assertEquals(0, tester.getAmount());
        assertEquals(LocalDateTime.MIN, tester.getDate());
    }

    @Test
    public void setAmount() {
        tester.setAmount(150);
        assertEquals(150, tester.getAmount());
        tester.setAmount(-10);
        assertEquals(150, tester.getAmount());
    }

    @Test
    public void getDate() {
        assertEquals(dateTester, tester.getDate());
    }

    @Test
    public void setDate() {
        LocalDateTime date = LocalDateTime.now();
        tester.setDate(date);
        assertEquals(date, tester.getDate());
    }

    @Test
    public void emptyAllowance(){
        tester = new Allowance();
        assertEquals(0, tester.getAmount());
        assertEquals(LocalDateTime.MIN, tester.getDate());
    }
}