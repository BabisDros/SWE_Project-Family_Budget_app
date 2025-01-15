package com.android.familybudgetapp.view.budget.allocateSurplus;

import static org.junit.Assert.assertEquals;

import android.util.Pair;

import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.MoneyBox;
import com.android.familybudgetapp.domain.MonthlySurplus;
import com.android.familybudgetapp.domain.User;

import org.junit.Before;
import org.junit.Test;

import java.time.YearMonth;
import java.util.ArrayList;

public class AllocateSurplusPresenterTest {
    private AllocateSurplusPresenter presenter;
    private AllocateSurplusViewStub stubView;
    private Family family;
    private User user;
    private MoneyBox moneyBox;

    @Before
    public void setUp() {
        presenter = new AllocateSurplusPresenter();
        stubView = new AllocateSurplusViewStub();
        family = new Family("testFamily");
        user = new User("testUser", "testUser", "testPass", FamPos.Protector, family);
        moneyBox = new MoneyBox("testReason", 10000); // 100EUR
        user.addMoneyBox(moneyBox);
        family.addMember(user);

        MonthlySurplus surplus = new MonthlySurplus(YearMonth.now().minusMonths(1), 100000); // 1.000EUR
        family.addSurplus(surplus);
        presenter.setView(stubView);
        presenter.setFamily(family);
    }

    /**
     * Tests that the family object can be set and retrieved from the presenter.
     */
    @Test
    public void testSetGetFamily() {
        presenter.setFamily(family);
        assertEquals(family, presenter.getFamily());
    }

    /**
     * Tests that the presenter correctly retrieves the previous month's surplus object from the family.
     */
    @Test
    public void testGetPreviousSurplusObj() {
        MonthlySurplus presenterPrevious = presenter.getPreviousSurplusObj();
        MonthlySurplus familyPrevious = family.getMonthlySurpluses().get(YearMonth.now().minusMonths(1));
        assertEquals(familyPrevious, presenterPrevious);
    }

    /**
     * Tests that a valid amount can be successfully added to a MoneyBox,
     * updating both the surplus and the MoneyBox amount correctly.
     */
    @Test
    public void testAddToMoneyBox_ValidAmount() {
        presenter.addToMoneyBox("10.00", moneyBox);
        assertEquals(100000 - 1000, family.getMonthlySurpluses().get(YearMonth.now().minusMonths(1)).getSurplus());
        assertEquals(1000, moneyBox.getCurrentAmount()); // 10 euros added
    }

    /**
     * Tests that an error message is shown when attempting to add an invalid amount (empty)
     */
    @Test
    public void testAddToMoneyBox_EmptyInvalidAmount() {
        presenter.addToMoneyBox("", moneyBox);
        assertEquals("Please enter a valid amount", stubView.getErrorMsg());
    }

    /**
     * Tests that an error message is shown when attempting to add an invalid amount 0
     */
    @Test
    public void testAddToMoneyBox_ZeroInvalidAmount() {
        presenter.addToMoneyBox("0", moneyBox);
        assertEquals("Please enter a valid amount", stubView.getErrorMsg());
    }

    /**
     * Tests that an error message is shown when attempting to add an amount exceeding the available surplus.
     */
    @Test
    public void testAddToMoneyBox_ExceedsSurplus() {
        presenter.addToMoneyBox("2000.00", moneyBox);
        assertEquals("The amount exceeds the available surplus", stubView.getErrorMsg());
    }

    /**
     * Tests that an error message is shown when attempting to add an amount that exceeds the target amount of the MoneyBox.
     */
    @Test
    public void testAddToMoneyBox_ExceedsTarget() {
        presenter.addToMoneyBox("200.00", moneyBox); // Adding 2 euros exceeds target
        assertEquals("Adding this amount will exceed the target amount for the MoneyBox", stubView.getErrorMsg());
    }

    /**
     * Tests that the presenter retrieves the correct list of user-MoneyBox pairs.
     */
    @Test
    public void testGetUserMoneyBoxes() {
        ArrayList<Pair<User, MoneyBox>> userMoneyBoxes = presenter.getUserMoneyBoxes();
        assertEquals(1, userMoneyBoxes.size());
    }
}