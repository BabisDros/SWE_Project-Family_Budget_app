package com.android.familybudgetapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class TesterTest {

    @Test
    public void getId() {
        Tester test = new Tester(10);
        assertEquals(10, test.getId());
    }
}