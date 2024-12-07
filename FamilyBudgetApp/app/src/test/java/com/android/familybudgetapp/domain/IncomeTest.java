package com.android.familybudgetapp.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class IncomeTest
{

    Income income;

    @Before
    public void setUp() {
        income =new Income("test");
    }

    @Test
    public void instantiateObjectWithNotAcceptedArguments()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            new Income("$");;
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            new Income(" ");;
        });
    }

    @Test
    public void setNullName()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            income.setName(null);
        });
    }

    @Test
    public void setNotAcceptedNameFormat()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            income.setName("");
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            income.setName(" ");
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            income.setName("?");
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            income.setName("  test");
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            income.setName("test ");
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            income.setName(" test ");
        });
    }

    @Test
    public void setAcceptedName()
    {
        String testName="test sta";
        income.setName(testName);
        assertEquals(testName, income.getName());

        testName="test";
        income.setName(testName);
        assertEquals(testName, income.getName());

        testName="1test1";
        income.setName(testName);
        assertEquals(testName, income.getName());
    }
}