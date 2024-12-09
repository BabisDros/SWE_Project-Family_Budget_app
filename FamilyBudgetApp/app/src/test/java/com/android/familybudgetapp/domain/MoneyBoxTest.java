package com.android.familybudgetapp.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MoneyBoxTest {

    public MoneyBox tester;
    @Before
    public void setUp(){
        tester = new MoneyBox("Reason", 100);
    }

    @Test public void instantiateObjectWithInvalidArguments() {
        assertThrows(IllegalArgumentException.class, ()-> {
            new MoneyBox(" test ", 100);
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            new MoneyBox(" ", 100);
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            new MoneyBox("", 100);
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            new MoneyBox(null, 100);
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            new MoneyBox("OK", -100);
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            new MoneyBox("OK", 0);
        });
    }
    @Test
    public void getReason() {
        assertEquals("Reason", tester.getReason());
    }

    @Test
    public void setReason() {
        tester.setReason("New reason");
        assertEquals("New reason", tester.getReason());
    }

    @Test
    public void getTarget() {
        assertEquals(100, tester.getTarget());
    }

    @Test
    public void setTarget() {
        tester.setTarget(150);
        assertEquals(150, tester.getTarget());
    }

    @Test
    public void getCurrentAmount() {
        assertEquals(0, tester.getCurrentAmount());
        tester.addMoney(new Allowance(40, LocalDateTime.now()));
        tester.addMoney(new Allowance(30, LocalDateTime.now()));
        assertEquals(70, tester.getCurrentAmount());
    }

    @Test
    public void addMoney() {
        assertEquals(0, tester.getCurrentAmount());

        assertThrows(IllegalArgumentException.class, ()-> {
            tester.addMoney(new Allowance());
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            tester.addMoney(new Allowance(tester.getTarget()+1, LocalDateTime.now()));
        });

        assertTrue(tester.addMoney(new Allowance(15, LocalDateTime.now())));
        assertTrue(tester.addMoney(new Allowance(20, LocalDateTime.of(2060, 10, 5, 6, 30))));
        assertEquals(35, tester.getCurrentAmount());
    }

    @Test
    public void addGetAllowances() {
        List<Allowance> expectedList = new ArrayList<>();
        assertEquals(expectedList, tester.getAllowances());
        Allowance obj1 = new Allowance(15, LocalDateTime.now());
        Allowance obj2 = new Allowance(20, LocalDateTime.of(2060, 10, 5, 6, 30));

        expectedList.add(obj1);
        expectedList.add(obj2);

        tester.addMoney(obj1);
        tester.addMoney(obj2);

        assertEquals(expectedList, tester.getAllowances());

    }

    @Test
    public void isTargetReached() {
        tester.addMoney(new Allowance(40, LocalDateTime.now()));
        tester.addMoney(new Allowance(30, LocalDateTime.now()));
        assertFalse(tester.isTargetReached());
        tester.addMoney(new Allowance(30, LocalDateTime.now()));
        assertTrue(tester.isTargetReached());
    }





}