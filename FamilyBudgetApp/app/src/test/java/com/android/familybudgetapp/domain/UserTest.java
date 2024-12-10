package com.android.familybudgetapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;


public class UserTest {
    private User user;
    private Family family;
    private FamPos familyPosition;

    @Before
    public void setup() {
        family = new Family("testFamily");
        familyPosition = FamPos.Protector;
        user = new User("John Doe", "john_doe", "password123", familyPosition, family);
    }

    @Test
    public void testUserCreation() {
        assertEquals("John Doe", user.getName());
        assertEquals("john_doe", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals(4, user.getID());
        assertEquals(familyPosition, user.getFamilyPosition());
        assertEquals(family, user.getFamily());
        assertNotNull(user.getCashFlows());
        assertEquals(0, user.getCashFlows().size());
    }

    @Test
    public void testAddCashFlow() {
        CashFlow cashFlow1 = new OneOff(100, new Expense("test",100), LocalDateTime.now().plusDays(1));
        CashFlow cashFlow2 = new OneOff(150, new Income("testName"), LocalDateTime.now().plusDays(1));

        user.addCashFlow(cashFlow1);
        user.addCashFlow(cashFlow2);

        List<CashFlow> cashFlows = user.getCashFlows();
        assertEquals(2, cashFlows.size());
        assertTrue(cashFlows.contains(cashFlow1));
        assertTrue(cashFlows.contains(cashFlow2));

        assertThrows(IllegalArgumentException.class, () -> {user.addCashFlow(null);});
        assertThrows(IllegalArgumentException.class, () -> {user.addCashFlow(new OneOff(-150, new Income("testName"), LocalDateTime.now()));});
    }


    @Test
    public void testSettersAndGetters() {
        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName());
        assertThrows(IllegalArgumentException.class, () -> {user.setName(null);});
        assertThrows(IllegalArgumentException.class, () -> {user.setName("Invalid#Name");});

        user.setUsername("jane_doe");
        assertEquals("jane_doe", user.getUsername());
        assertThrows(IllegalArgumentException.class, () -> {user.setUsername(null);});
        assertThrows(IllegalArgumentException.class, () -> {user.setUsername("%$");});

        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
        assertThrows(IllegalArgumentException.class, () -> {user.setPassword(null);});



        FamPos newFamilyPosition = FamPos.Member;
        user.setFamilyPosition(newFamilyPosition);
        assertEquals(newFamilyPosition, user.getFamilyPosition());
        assertThrows(IllegalArgumentException.class, () -> {user.setFamilyPosition(null);});


        Family newFamily = new Family("Family2");
        user.setFamily(newFamily);
        assertEquals(newFamily, user.getFamily());
        assertThrows(IllegalArgumentException.class, () -> {user.setFamily(null);});

    }

    @Test
    public void testToString() {
        String expected = "User{id=" + user.getID() +
                ", familyPosition=" + user.getFamilyPosition() +
                ", name='" + user.getName() + "'" +
                ", username='" + user.getUsername() + "'" +
                ", password='" + user.getPassword() + "'" +
                ", family=" + user.getFamily() +
                ", cashFlows=" + user.getCashFlows() + "}";
        assertEquals(expected, user.toString());
    }
}
