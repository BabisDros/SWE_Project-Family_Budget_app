package com.android.familybudgetapp.domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;


public class FamilyTest
{
    private Family family;
    private String name = "TestFamily";

    @Before
    public void setUp()
    {
        family = new Family(name);
    }

    @Test
    public void instantiateObjectWithInvalidArguments()
    {
        assertThrows(IllegalArgumentException.class, ()-> {
            new Family(" test ");
        });

        assertThrows(IllegalArgumentException.class, ()-> {
            new Family(" ");
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            new Family(null);
        });

    }

    @Test
    public void getID() throws NoSuchFieldException, IllegalAccessException
    {
        // Temporary get access to private field to reset it,
        // instead of creating a method in the Class specifically for the test.
        Field field = Family.class.getDeclaredField("nextId");
        field.setAccessible(true);
        field.set(null, 1);

        Family family1 = new Family("TestFamily");
        Family family2 = new Family("TestFamily2");
        Family family3 = new Family("TestFamily3");
        assertEquals(3, family3.getID());
    }

    @Test
    public void getName()
    {
        assertEquals(name, family.getName());
    }

    @Test
    public void getSavings()
    {
        int amount = 10;
        family.addToSavings(amount);
        assertEquals(amount, family.getSavings());
    }

    @Test
    public void getYearlySavings()
    {
        int amount = 10;
        family.addToSavings(amount);
        assertEquals(amount, family.getYearlySavings());
    }

    @Test
    public void getMembers()
    {
        User member1 = new User("john", "1234", "12345", FamPos.Member, family);
        User member2 = new User("doe", "5678", "5678", FamPos.Protector, family);
        Map<Long, User> testMap = new HashMap<>();
        testMap.put(member1.getID(), member1);
        testMap.put(member2.getID(), member2);

        family.addMember(member1);
        family.addMember(member2);
        assertEquals(testMap, family.getMembers());
    }

    @Test
    public void getMonthlySurpluses()
    {
        MonthlySurplus surplus1 = new MonthlySurplus(LocalDateTime.now());
        MonthlySurplus surplus2 = new MonthlySurplus(LocalDateTime.now().plusMonths(1));

        Map<YearMonth, MonthlySurplus> testSet = new HashMap<>();
        testSet.put(YearMonth.of(surplus1.getDate().getYear(), surplus1.getDate().getMonth()), surplus1);
        testSet.put(YearMonth.of(surplus2.getDate().getYear(), surplus2.getDate().getMonth()), surplus2);

        family.addSurplus(surplus1);
        family.addSurplus(surplus2);

        assertEquals(testSet, family.getMonthlySurpluses());
    }

    @Test
    public void getCurrentSurplus()
    {
        MonthlySurplus surplus = new MonthlySurplus(LocalDateTime.now());
        family.addSurplus(surplus);
        assertEquals(surplus, family.getCurrentSurplus());
    }

    @Test
    public void getCashFlowCategories()
    {
        CashFlowCategory category1 = new Expense("test", 10);
        CashFlowCategory category2 = new Expense("test2", 20);

        Map<String, CashFlowCategory> testSet = new HashMap<>();
        testSet.put(category1.getName(),category1);
        testSet.put(category2.getName(),category2);

        family.addCashFlowCategory(category1);
        family.addCashFlowCategory(category2);

        assertEquals(testSet, family.getCashFlowCategories());
    }

    @Test
    public void setAcceptedNameFormat()
    {
        String name = "123 fds2";
        family.setName(name);
        assertEquals(name, family.getName());
    }

    @Test
    public void setNameNull()
    {
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.setName(null);
        });
    }

    @Test
    public void setNotAcceptedNameFormat()
    {
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.setName("");
        });

        assertThrows(IllegalArgumentException.class, () ->
        {
            family.setName(" ");
        });

        assertThrows(IllegalArgumentException.class, () ->
        {
            family.setName("?");
        });
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.setName("  test");
        });
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.setName("test ");
        });
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.setName(" test ");
        });
    }

    @Test
    public void addToSavingsValidAmount()
    {
        int amount = 100;
        family.addToSavings(amount);
        family.addToSavings(amount);
        assertEquals(amount * 2, family.getSavings());
    }


    @Test
    public void addToSavingsWrongAmount()
    {
        int amount = -100;
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.addToSavings(amount);
        });
    }

    @Test
    public void canAddToSavingsValidAmount()
    {
        assertTrue(family.canAddToSavings(100));
        assertTrue(family.canAddToSavings(0));
    }

    @Test
    public void canAddToSavingsNegativeAmount()
    {
        assertFalse(family.canAddToSavings(-1));
    }

    @Test
    public void removeFromSavingsValidAmount()
    {
        int amount=10;
        family.addToSavings(amount);
        family.removeFromSavings(amount);
        assertEquals(0, family.getSavings());
        assertEquals(0, family.getYearlySavings());
    }

    @Test
    public void removeFromSavingsInvalidAmount()
    {
        //remove from zero savings
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.removeFromSavings(1);
        });

        family.addToSavings(10);
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.removeFromSavings(11);
        });
    }

    @Test
    public void canRemoveFromSavingsValidAmount()
    {
        family.addToSavings(10);
        assertTrue(family.canRemoveFromSavings(10));
    }

    @Test
    public void canRemoveFromSavingsInvalidAmount()
    {
        assertFalse(family.canRemoveFromSavings(-1));

        //remove from zero savings
        assertFalse(family.canRemoveFromSavings(1));

        family.addToSavings(10);
        assertFalse(family.canRemoveFromSavings(11));
    }

    @Test
    public void addValidMember()
    {
        User member=new User("john","1234","12345",FamPos.Member, family );
        family.addMember(member);
        assertTrue(family.getMembers().containsKey(member.getID()));
    }

    @Test
    public void addInvalidMember()
    {
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.addMember(null);
        });

        User member=new User("john","1234","12345",FamPos.Member, family );
        family.addMember(member);
        //try to add already existing member
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.addMember(member);
        });
    }

    @Test
    public void validateValidMember()
    {
        User member1=new User("john","1234","12345",FamPos.Member, family );
        User member2=new User("jane","1234","12345",FamPos.Member, family );
        //try to add already existing member
        assertTrue(family.validateMember(member1));
        assertTrue(family.validateMember(member2));
    }
    @Test
    public void validateInvalidMember()
    {
        User member=new User("john","1234","12345",FamPos.Member, family );
        family.addMember(member);

        //try to add already existing member
        assertFalse(family.validateMember(member));
    }

    @Test
    public void addValidSurplus()
    {
        MonthlySurplus surplus=new MonthlySurplus(LocalDateTime.now());

        family.addSurplus(surplus);
        assertTrue(family.getMonthlySurpluses().containsKey(YearMonth.of(surplus.getDate().getYear(),surplus.getDate().getMonth()) ));
    }

    @Test
    public void addInvalidSurplus()
    {
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.addSurplus(null);
        });

        MonthlySurplus surplus=new MonthlySurplus(LocalDateTime.now());
        family.addSurplus(surplus);
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.addSurplus(surplus);
        });
    }

    @Test
    public void validateValidSurplus()
    {
        MonthlySurplus surplus1=new MonthlySurplus(LocalDateTime.now());
        MonthlySurplus surplus2=new MonthlySurplus(LocalDateTime.now().plusMonths(1));

        family.addSurplus(surplus1);

        //add different date surplus
        assertTrue(family.validateSurplus(surplus2));
    }

    @Test
    public void validateInvalidSurplus()
    {
        MonthlySurplus surplus=new MonthlySurplus(LocalDateTime.now());
        family.addSurplus(surplus);

        //add same date surplus
        assertFalse(family.validateSurplus(surplus));
    }

    @Test
    public void addValidCashFlowCategory()
    {
        CashFlowCategory category1=new Expense("test",10);
        CashFlowCategory category2=new Expense("test2",10);
        CashFlowCategory category3=new Income("test3");//
        family.addCashFlowCategory(category1);
        family.addCashFlowCategory(category2);
        family.addCashFlowCategory(category3);
        assertTrue(family.getCashFlowCategories().containsKey(category1.getName()));
        assertTrue(family.getCashFlowCategories().containsKey(category2.getName()));
        assertTrue(family.getCashFlowCategories().containsKey(category3.getName()));
    }

    @Test
    public void addInvalidCashFlowCategory()
    {
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.addCashFlowCategory(null);
        });

        CashFlowCategory expense1=new Expense("test",10);
        CashFlowCategory expense2=new Expense("test",10);
        CashFlowCategory income1 =new Income("test1");
        CashFlowCategory income2 =new Income("test1");

        family.addCashFlowCategory(expense1);
        family.addCashFlowCategory(income1);

        assertThrows(IllegalArgumentException.class, () ->
        {
            family.addCashFlowCategory(expense2);
        });
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.addCashFlowCategory(income2);
        });
    }

    @Test
    public void validatedValidCashFlowCategory()
    {
        CashFlowCategory expense1=new Expense("test",10);
        CashFlowCategory expense2=new Expense("test2",10);
        CashFlowCategory income1 =new Income("test3");
        CashFlowCategory income2 =new Income("test4");

        family.addCashFlowCategory(expense1);
        family.addCashFlowCategory(income1);

        assertTrue(family.validateCashFlowCategory(expense2));
        assertTrue(family.validateCashFlowCategory(income2));
    }

    @Test
    public void validatedInvalidCashFlowCategory()
    {
        CashFlowCategory expense1=new Expense("test",10);
        CashFlowCategory expense2=new Expense("test",10);
        CashFlowCategory income1 =new Income("test1");
        CashFlowCategory income2 =new Income("test1");

        family.addCashFlowCategory(expense1);
        family.addCashFlowCategory(income1);

        assertFalse(family.validateCashFlowCategory(expense2));
        assertFalse(family.validateCashFlowCategory(income2));
    }

    @Test
    public void removeNullCashFlowCategory()
    {
        assertThrows(IllegalArgumentException.class, () ->
        {
            family.removeCashFlowCategory(null);
        });
    }

    @Test
    public void removeValidCashFlowCategory()
    {
        CashFlowCategory expense1=new Expense("test",10);
        family.addCashFlowCategory(expense1);
        family.removeCashFlowCategory(expense1);
        assertFalse(family.getCashFlowCategories().containsKey(expense1.getName()));
    }

    @Test
    public void resetYearSavings()
    {
        family.addToSavings(100);
        family.resetYearSavings();
        assertEquals(0, family.getYearlySavings());
    }
}