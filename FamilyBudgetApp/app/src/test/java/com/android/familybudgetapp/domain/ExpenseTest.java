package com.android.familybudgetapp.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ExpenseTest
{
    Expense expense;

    @Before
    public void setUp()
    {
        expense = new Expense("test",20);
    }

    @Test
    public void instantiateObjectWithInvalidArguments()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            new Expense("test",-1);
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            new Expense(" ",1);
        });
    }

    @Test
    public void setNegativeLimit()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            expense.setLimit(-20);
        });
    }

    @Test
    public void setAcceptedLimit()
    {
        expense.setLimit(1);
        assertEquals(1, expense.getLimit());
    }

    @Test
    public void getLimit()
    {
        assertEquals(20, expense.getLimit());
    }

    @Test
    public void setNullName()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            expense.setName(null);
        });
    }

    @Test
    public void setNotAcceptedNameFormat()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            expense.setName("");
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            expense.setName(" ");
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            expense.setName("?");
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            expense.setName("  test");
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            expense.setName("test ");
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            expense.setName(" test ");
        });
    }

    @Test
    public void setAcceptedName()
    {
        String testName="test sta";
        expense.setName(testName);
        assertEquals(testName, expense.getName());

        testName="test";
        expense.setName(testName);
        assertEquals(testName, expense.getName());

        testName="1test1";
        expense.setName(testName);
        assertEquals(testName, expense.getName());
    }
}