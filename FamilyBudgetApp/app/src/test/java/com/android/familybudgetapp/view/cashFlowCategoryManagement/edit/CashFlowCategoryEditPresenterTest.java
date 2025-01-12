package com.android.familybudgetapp.view.cashFlowCategoryManagement.edit;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.create.CashFlowCategoryCreateActivity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class CashFlowCategoryEditPresenterTest
{
    private CashFlowCategoryEditPresenter presenter;
    private CashFlowCategoryEditViewStub viewStub;
    private Family currentFamily;

    @Before
    @Test
    public void setUp()
    {
        new MemoryInitializer().prepareData();
        presenter = new CashFlowCategoryEditPresenter();
        viewStub = new CashFlowCategoryEditViewStub();

        UserDAO userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();

        presenter.setView(viewStub);
        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);

        User currentUser = userDAO.findByUsername("usernameTest");
        Initializer.currentUserID = currentUser.getID();
        currentFamily = currentUser.getFamily();
    }

    @Test
    public void setExistingCashFlowCategoryData()
    {
        String categoryName = "Food";
        presenter.setCashFlowCategoryData(categoryName);
        assertEquals(categoryName, viewStub.getName());
        assertEquals("20000", viewStub.getLimit());
        assertTrue(viewStub.isSpinnerSet());
    }

    @Test
    public void setNotExistingCashFlowCategoryData()
    {
        String categoryName = "ood";
        presenter.setCashFlowCategoryData(categoryName);
        assertEquals(categoryName, viewStub.getName());
    }

    @Test
    public void setIncomeCashFlowCategoryData()
    {
        String categoryName = "Job";
        presenter.setCashFlowCategoryData(categoryName);
        assertEquals(categoryName, viewStub.getName());
    }

    @Test
    public void saveInvalidName()
    {
        String categoryName = "1";
        presenter.save(categoryName, "10");
        assertNull(currentFamily.getCashFlowCategories().get(categoryName));
    }

    @Test
    public void saveExpenseSameNameValidLimit()
    {
        String categoryName = "Food";
        int newLimit = 10;
        presenter.setType(CashFlowCategoryCreateActivity.EXPENSE);
        presenter.setCashFlowCategoryData(categoryName);
        presenter.save(categoryName, String.valueOf(newLimit));
        Expense changedCategory = (Expense) currentFamily.getCashFlowCategories().get(categoryName.toLowerCase());

        assertNotNull(changedCategory);
        assertEquals(newLimit, changedCategory.getLimit());
    }

    @Test
    public void saveExpenseExistingName()
    {
        String categoryNameToEdit = "Food";
        String newExistingName = "Clothes";
        int newLimit = 10;

        presenter.setType(CashFlowCategoryCreateActivity.EXPENSE);
        presenter.setCashFlowCategoryData(categoryNameToEdit);
        presenter.save(newExistingName, String.valueOf(newLimit));

        Expense categoryToEdit = (Expense) currentFamily.getCashFlowCategories().get(categoryNameToEdit.toLowerCase());
        assertNotNull(categoryToEdit);
    }

    @Test
    public void saveExpenseDifferentName()
    {
        String categoryNameToEdit = "Food";
        String newName = "ChangedName";
        int newLimit = 10;
        presenter.setType(CashFlowCategoryCreateActivity.EXPENSE);
        presenter.setCashFlowCategoryData(categoryNameToEdit);
        presenter.save(newName, String.valueOf(newLimit));
        Expense initialCategory = (Expense) currentFamily.getCashFlowCategories().get(categoryNameToEdit.toLowerCase());
        Expense changedCategory = (Expense) currentFamily.getCashFlowCategories().get(newName.toLowerCase());

        assertNull(initialCategory);
        assertNotNull(changedCategory);
        assertEquals(newLimit, changedCategory.getLimit());
        assertEquals(1, viewStub.getOverviewCounter());
    }

    @Test
    public void saveExpenseValidNameInvalidLimit()
    {
        //negative limit
        String categoryName = "Food";
        int newLimit = -10;
        presenter.setType(CashFlowCategoryCreateActivity.EXPENSE);
        presenter.setCashFlowCategoryData(categoryName);
        presenter.save(categoryName, String.valueOf(newLimit));
        Expense changedCategory = (Expense) currentFamily.getCashFlowCategories().get(categoryName.toLowerCase());

        assertNotNull(changedCategory);
        assertNotEquals(newLimit, changedCategory.getLimit());
    }
}
