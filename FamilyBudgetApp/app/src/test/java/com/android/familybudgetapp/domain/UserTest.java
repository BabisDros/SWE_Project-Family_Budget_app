package com.android.familybudgetapp.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Date;


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
        assertEquals(familyPosition, user.getFamilyPosition());
        assertEquals(family, user.getFamily());
        assertNotNull(user.getCashFlows());
        assertEquals(0, user.getCashFlows().size());
    }

    @Test
    public void testAddCashFlow() {
        CashFlow cashFlow1 = new CashFlow(100, new Expense(100), new Date());
        CashFlow cashFlow2 = new CashFlow(-50, new Income(), new Date());

        user.addCashFlow(cashFlow1);
        user.addCashFlow(cashFlow2);

        List<CashFlow> cashFlows = user.getCashFlows();
        assertEquals(2, cashFlows.size());
        assertTrue(cashFlows.contains(cashFlow1));
        assertTrue(cashFlows.contains(cashFlow2));
    }

    @Test
    public void testVerifyCredentials() {
        assertTrue(user.verifyCredentials("john_doe", "password123"));
        assertFalse(user.verifyCredentials("john_doe", "wrongpassword"));
        assertFalse(user.verifyCredentials("wronguser", "password123"));
    }

    @Test
    public void testSettersAndGetters() {
        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName());

        user.setUsername("jane_doe");
        assertEquals("jane_doe", user.getUsername());

        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());

        FamPos newFamilyPosition = FamPos.Member;
        user.setFamilyPosition(newFamilyPosition);
        assertEquals(newFamilyPosition, user.getFamilyPosition());

        Family newFamily = new Family("Family2");
        user.setFamily(newFamily);
        assertEquals(newFamily, user.getFamily());
    }
}
