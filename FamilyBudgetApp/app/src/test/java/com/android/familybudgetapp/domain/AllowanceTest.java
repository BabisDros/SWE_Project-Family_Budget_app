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
    public void instantiateObjectWithInvalidArguments() {
        assertThrows(IllegalArgumentException.class, ()-> {
                    new Allowance(-10, dateTester);
                });
        assertThrows(IllegalArgumentException.class, ()-> {
            new Allowance(0, dateTester);
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            new Allowance(20, null);
        });
    }

    @Test
    public void setAmount() {
        tester.setAmount(150);
        assertEquals(150, tester.getAmount());
        assertThrows(IllegalArgumentException.class, ()-> {
            tester.setAmount(-10);
        });
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