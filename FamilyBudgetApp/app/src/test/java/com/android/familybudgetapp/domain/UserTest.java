package com.android.familybudgetapp.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import com.android.familybudgetapp.utilities.PBKDF2Hashing;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


public class UserTest {
    private User user;
    private Family family;
    private FamPos familyPosition;
    private Long currentId;

    @Before
    public void setup() {
        family = new Family("testFamily");
        familyPosition = FamPos.Protector;
        currentId = User.getNextID();
        user = new User("John Doe", "john_doe", "password123", familyPosition, family);
    }

    @Test
    public void testUserCreation() throws Exception
    {
        assertEquals("John Doe", user.getName());
        assertEquals("john_doe", user.getUsername());
        assertTrue(PBKDF2Hashing.verifyPassword("password123",user.getPassword()) );
        assertEquals(currentId, user.getID());
        assertEquals(familyPosition, user.getFamilyPosition());
        assertEquals(family, user.getFamily());
        assertNotNull(user.getCashFlows());
        assertEquals(0, user.getCashFlows().size());
    }


    @Test
    public void testUserCreationWithNonExistingHashAlgorithm()
    {
        family = new Family("testFamily");
        familyPosition = FamPos.Protector;
        PBKDF2Hashing.hashAlgorithm = "nonExistingAlgorithmInSystem";

        assertThrows(RuntimeException.class, () ->
        {
            new User("John Doe", "john_doe", "password123", FamPos.Protector, new Family("testFamily"));
        });
        //revert to the common hashing algorithm else all other tests fail
        PBKDF2Hashing.hashAlgorithm =PBKDF2Hashing.HmacSHA256;
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
    public void testSettersAndGetters() throws Exception
    {
        user.setName("Jane Doe");
        assertEquals("Jane Doe", user.getName());
        assertThrows(IllegalArgumentException.class, () -> {user.setName(null);});
        assertThrows(IllegalArgumentException.class, () -> {user.setName("Invalid#Name");});

        user.setUsername("jane_doe");
        assertEquals("jane_doe", user.getUsername());
        assertThrows(IllegalArgumentException.class, () -> {user.setUsername(null);});
        assertThrows(IllegalArgumentException.class, () -> {user.setUsername("%$");});

        user.setPassword("passwordTest");
        assertTrue(PBKDF2Hashing.verifyPassword("passwordTest",user.getPassword()) );
        assertThrows(IllegalArgumentException.class, () -> {user.setPassword(null);});
        assertThrows(IllegalArgumentException.class, () -> {user.setPassword("a");});



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

    @Test
    public void testMoneyBox() {
        MoneyBox moneyBox = new MoneyBox("Test", 100);
        MoneyBox alreadyExists = new MoneyBox("Test", 100);
        MoneyBox nullMoneyBox = null;

        user.addMoneyBox(moneyBox);

        HashMap<String, MoneyBox> moneyBoxes = new HashMap<>();
        moneyBoxes.put("Test", moneyBox);

        assertEquals(moneyBoxes, user.getMoneyBoxes());
        assertThrows(IllegalArgumentException.class, () -> {user.addMoneyBox(alreadyExists);});
        assertThrows(IllegalArgumentException.class, () -> {user.addMoneyBox(nullMoneyBox);});
    }
}
