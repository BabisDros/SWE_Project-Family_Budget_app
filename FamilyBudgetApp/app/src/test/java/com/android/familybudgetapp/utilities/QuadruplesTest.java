package com.android.familybudgetapp.utilities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class QuadruplesTest {

    private Quadruples<String, Integer, Integer, Object> obj1;
    private LocalDateTime localDateTime;
    @Before
    public void setUp(){
        localDateTime = LocalDateTime.now();
        obj1 = new Quadruples<>("Test", 100, 78, localDateTime);
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
    public void getThird() {
        assertEquals(Integer.valueOf(78), obj1.getThird());
    }

    @Test
    public void getFourth() {
        assertEquals(localDateTime, obj1.getFourth());
    }

    @Test
    public void testEquals() {
        Quadruples<String, String, String, String> obj2 = new Quadruples<>("", "", "", "");
        Quadruples<String, String, String, String> obj3 = new Quadruples<>("Test", "", "", "");
        Quadruples<String, Integer, String, String> obj4 = new Quadruples<>("Test", 100, "", "");
        Quadruples<String, Integer, Integer, String> obj5 = new Quadruples<>("Test", 100, 78, "");
        Quadruples<String, Integer, Integer, LocalDateTime> obj6 = new Quadruples<>("Test", 100, 78, localDateTime);
        assertFalse(obj1.equals(null));
        assertTrue(obj1.equals(obj1));
        assertFalse(obj1.equals(Integer.valueOf(100)));
        assertFalse(obj1.equals(obj2));
        assertFalse(obj1.equals(obj3));
        assertFalse(obj1.equals(obj4));
        assertFalse(obj1.equals(obj5));
        assertTrue(obj1.equals(obj6));

    }
}