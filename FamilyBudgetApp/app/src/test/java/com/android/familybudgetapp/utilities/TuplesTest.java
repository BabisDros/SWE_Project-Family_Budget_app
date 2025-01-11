package com.android.familybudgetapp.utilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TuplesTest {

    private Tuples<String, Integer> obj1;

    @Before
    public void setUp(){
        obj1 = new Tuples<>("Test", 100);
    }

    @Test
    public void getFirst() {
        assertEquals("Test", obj1.getFirst());
    }

    @Test
    public void getSecond() {
        assertEquals(Integer.valueOf(100), obj1.getSecond());
    }

    @Test
    public void testEquals() {
        Tuples<String, String> obj2 = new Tuples<>("", "");
        Tuples<String, String> obj3 = new Tuples<>("Test", "");
        Tuples<String, Integer> obj4 = new Tuples<>("", 100);
        assertFalse(obj1.equals(null));
        assertTrue(obj1.equals(obj1));
        assertFalse(obj1.equals(Integer.valueOf(100)));
        assertFalse(obj1.equals(obj2));
        assertFalse(obj1.equals(obj3));
        assertFalse(obj1.equals(obj4));
    }
}