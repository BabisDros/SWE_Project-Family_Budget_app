package com.android.familybudgetapp.view.budget.showBudget;

import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.*;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.budget.cashFlowManager.CashFlowManagerInterface;
import com.android.familybudgetapp.view.budget.cashFlowManager.FamilyUserStrategy;
import com.android.familybudgetapp.view.budget.cashFlowManager.MonthlyManager;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNull;


public class BudgetPresenterTest {
    private BudgetPresenter presenter;
    private BudgetPresenterViewStub viewStub;
    private User currentUser;
    private UserDAO userDAO;
    private CashFlowManagerInterface cashFlowManager;
    private MonthlyManager familyMonthlySurplusManager;

    @Before
    public void setUp() {
        presenter = new BudgetPresenter();
        viewStub = new BudgetPresenterViewStub();
        userDAO = new UserDAOMemory();

        Family family = new Family("family");
        Expense exp = new Expense("groceries", 50000);
        family.addCashFlowCategory(exp);

        currentUser = new User("testUser", "testUser", "testPass", FamPos.Protector, family);
        currentUser.setFamily(family);
        family.addMember(currentUser);

        cashFlowManager = new MonthlyManager();
        cashFlowManager.setUserRetrievalStrategy(new FamilyUserStrategy(family));
        familyMonthlySurplusManager = new MonthlyManager();
        familyMonthlySurplusManager.setUserRetrievalStrategy(new FamilyUserStrategy(family));

        presenter.setView(viewStub);
        presenter.setUserDao(userDAO);
        presenter.setCashFlowManager(cashFlowManager);
        presenter.setFamilyMonthlySurplusManager(familyMonthlySurplusManager);
        presenter.setCurrentUser(currentUser);
    }

    /**
     * Tests adding a recurring cash flow with valid input.
     */
    @Test
    public void testAddCashFlow_ValidInput_Recurring() {
        LocalDateTime dateStart = LocalDateTime.now();
        LocalDateTime dateEnd = dateStart.plusMonths(1);

        presenter.addCashFlow("groceries", "150.00", true, dateStart, dateEnd, 0);

        assertNull(viewStub.getErrorMsg());
        assertEquals(1, currentUser.getCashFlows().size());
        List<CashFlow> cashFlowList = currentUser.getCashFlows();
        assertEquals(15000, cashFlowList.get(cashFlowList.size()-1).getAmount());
    }

    /**
     * Tests adding a one-time cash flow with valid input.
     */
    @Test
    public void testAddCashFlow_ValidInput_OneTime() {
        LocalDateTime dateStart = LocalDateTime.now();
        LocalDateTime dateEnd = dateStart.plusMonths(1);

        presenter.addCashFlow("groceries", "150.00", false, dateStart, dateEnd, 0);

        assertNull(viewStub.getErrorMsg());
        assertEquals(1, currentUser.getCashFlows().size());
        List<CashFlow> cashFlowList = currentUser.getCashFlows();
        assertEquals(15000, cashFlowList.get(cashFlowList.size()-1).getAmount());
    }

    /**
     * Tests handling of an invalid category input when adding cash flow.
     */
    @Test
    public void testAddCashFlow_InvalidCategory() {
        LocalDateTime dateStart = LocalDateTime.now();
        LocalDateTime dateEnd = dateStart.plusMonths(1);

        presenter.addCashFlow("", "150.00", true, dateStart, dateEnd, 0);
        assertEquals("Please select a category.", viewStub.getErrorMsg());
    }

    /**
     * Tests handling of invalid amount input when adding cash flow.
     */
    @Test
    public void testAddCashFlow_InvalidAmount() {
        LocalDateTime dateStart = LocalDateTime.now();
        LocalDateTime dateEnd = dateStart.plusMonths(1);

        presenter.addCashFlow("groceries", "0", true, dateStart, dateEnd, 0);
        assertEquals("Please enter a valid amount.", viewStub.getErrorMsg());

        presenter.addCashFlow("groceries", "", true, dateStart, dateEnd, 0);
        assertEquals("Please enter a valid amount.", viewStub.getErrorMsg());
    }

    /**
     * Tests handling of an invalid start date input when adding cash flow.
     */
    @Test
    public void testAddCashFlow_InvalidDateStart() {
        LocalDateTime dateStart = null;
        LocalDateTime dateEnd = LocalDateTime.now().plusMonths(1);

        presenter.addCashFlow("groceries", "150.00", true, dateStart, dateEnd, 0);
        assertEquals("Please enter a valid start date.", viewStub.getErrorMsg());
    }

    /**
     * Tests handling of an invalid end date input for recurring cash flow.
     */
    @Test
    public void testAddCashFlow_InvalidRecurringDateEnd() {
        LocalDateTime dateStart = LocalDateTime.now();
        LocalDateTime dateEnd = dateStart.minusDays(1);

        presenter.addCashFlow("groceries", "150.00", true, dateStart, dateEnd, 0);

        assertEquals("Please enter a valid end date.", viewStub.getErrorMsg());
    }

    /**
     * Tests calculation of the user's surplus.
     */
    @Test
    public void testCalculateSurplus() {
        int surplus = presenter.calculateSurplus();
        assertEquals(0, surplus);
    }

    /**
     * Tests updating the family's monthly surplus.
     */
    @Test
    public void testUpdateFamilySurplus() {
        Family family = currentUser.getFamily();
        YearMonth currentMonth = YearMonth.now();
        MonthlySurplus originalSurplus = new MonthlySurplus(currentMonth, 0);
        family.addSurplus(originalSurplus);

        int updatedSurplus = 500;
        presenter.updateFamilySurplus(updatedSurplus);

        assertEquals(updatedSurplus, family.getMonthlySurpluses().get(currentMonth).getSurplus());
    }

    /**
     * Tests retrieval of the surplus left from the previous month.
     */
    @Test
    public void testGetPreviousSurplusLeft() {
        Family family = currentUser.getFamily();
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        assertEquals(0, presenter.getPreviousSurplusLeft());
        family.addSurplus(new MonthlySurplus(previousMonth, 300));
        assertEquals(300, presenter.getPreviousSurplusLeft());
    }

    /**
     * Tests retrieval of expenses per category.
     */
    @Test
    public void getExpensePerCategory()
    {
        assertEquals(0, presenter.getExpensePerCategory().size());
    }

    /**
     * Tests retrieval of incomes per category.
     */
    @Test
    public void getIncomePerCategory()
    {
        assertEquals(0, presenter.getIncomePerCategory().size());
    }

    /**
     * Tests retrieval of the family monthly surplus manager.
     */
    @Test
    public void getFamilyMonthlySurplusManager() {
        assertEquals(familyMonthlySurplusManager, presenter.getFamilyMonthlySurplusManager());
    }

    /**
     * Tests surplus calculation considering user cash flows.
     */
    @Test
    public void calculateSurplus() {
        OneOff exp = new OneOff(100, new Expense("test", 100), LocalDateTime.now());
        currentUser.addCashFlow(exp);
        assertEquals(-100, presenter.calculateSurplus());
    }

    /**
     * Tests moving unallocated surplus from expired months to savings.
     */
    @Test
    public void testMoveUnallocatedSurplusToSavings() {
        YearMonth old = YearMonth.now().minusMonths(2);
        MonthlySurplus expiredSurplus = new MonthlySurplus(old, 1000);
        currentUser.getFamily().addSurplus(expiredSurplus);
        assertEquals(0, currentUser.getFamily().getSavings());
        presenter.moveUnallocatedSurplusToSavings();
        assertEquals(1000, currentUser.getFamily().getSavings());
    }
}
