package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.memorydao.UserDAOMemory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class CashFlowCategoryCreatePresenterTest
{
    private CashFlowCategoryCreatePresenter presenter;
    private CashFlowCategoryCreateViewStub viewStub;
    private User currentUser;

    @Before
    @Test
    public void setUp()
    {
        new MemoryInitializer().prepareData();
        presenter = new CashFlowCategoryCreatePresenter();
        viewStub = new CashFlowCategoryCreateViewStub();

        UserDAO userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();

        presenter.setView(viewStub);
        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);

        currentUser = userDAO.findByUsername("usernameTest");
        Initializer.currentUserID = currentUser.getID();
    }

    /**
     * Tests if the save method in the CashFlowCategoryCreatePresenter class
     * correctly handles an invalid category name by asserting that the category is not added to the family's cash flow categories.
     */
    @Test
    public void saveInvalidName()
    {
        String categoryName = "1";
        presenter.save(categoryName, "10");
        assertNull(currentUser.getFamily().getCashFlowCategories().get(categoryName));
    }

    /**
     * Tests if the save method in the CashFlowCategoryCreatePresenter class
     * correctly saves a valid name and valid limit by adding the category to the family's cash flow categories,
     * and asserting that the correct success message and title is displayed in the view stub.
     */
    @Test
    public void saveExpenseValidNameValidLimit()
    {
        String categoryName = "test";
        presenter.setType(CashFlowCategoryCreateActivity.EXPENSE);
        presenter.save(categoryName, "10");
        CashFlowCategory currentCategory = currentUser.getFamily().getCashFlowCategories().get(categoryName);
        assertNotNull(currentCategory);

        assertEquals(String.format(CashFlowCategoryCreatePresenter.SUCCESSFUL_ADDED_CATEGORY_TITLE, currentCategory.getName()), viewStub.getTitle());
        assertEquals(CashFlowCategoryCreatePresenter.ADD_EXTRA_CATEGORY_PROMPT, viewStub.getMsg());
    }

    /**
     * Tests if the save method in the CashFlowCategoryCreatePresenter class
     * correctly handles all invalid category limits (empty, zero, or negative) by not
     * adding the cashflowCategory to the family's cash flow categories.
     */
    @Test
    public void saveExpenseValidNameInvalidLimit()
    {
        presenter.setType(CashFlowCategoryCreateActivity.EXPENSE);

        String categoryName1 = "test1";
        presenter.save(categoryName1, "");
        assertNull(currentUser.getFamily().getCashFlowCategories().get(categoryName1));

        String categoryName2 = "test2";
        presenter.save(categoryName2, "0");
        assertNull(currentUser.getFamily().getCashFlowCategories().get(categoryName2));

        String categoryName3 = "test3";
        presenter.save(categoryName3, "-1");
        assertNull(currentUser.getFamily().getCashFlowCategories().get(categoryName3));
    }

    /**
     * Tests if the save method in the CashFlowCategoryCreatePresenter class
     * correctly saves a valid category name for an income category by adding the category to the family's cash flow categories.
     */
    @Test
    public void saveIncomeValidName()
    {
        presenter.setType(CashFlowCategoryCreateActivity.INCOME);

        String categoryName = "test";
        presenter.save(categoryName, "");
        assertNotNull(currentUser.getFamily().getCashFlowCategories().get(categoryName));
    }

    /**
     * Tests if the resetFields method in the CashFlowCategoryCreatePresenter class
     * correctly calls the view's clearFields method by counting the numbers of calls.
     */
    @Test
    public void resetFields()
    {
        presenter.resetFields();
        assertEquals(1, viewStub.getClearFieldsCount());
    }

    /**
     * Tests if the validateName method in the CashFlowCategoryCreatePresenter class
     * correctly validates a unique category name by asserting that the method returns true
     * when a new, different category name is provided.
     */
    @Test
    public void validateUniqueName()
    {
        presenter.setType(CashFlowCategoryCreateActivity.INCOME);

        presenter.save("test", "");
        assertTrue(presenter.validateName("test2"));
    }

    /**
     * Tests if the validateName method in the CashFlowCategoryCreatePresenter class
     * correctly validates a unique category name by asserting that the method returns false
     * when an existing category name is provided.
     */
    @Test
    public void validateNotUniqueName()
    {
        presenter.setType(CashFlowCategoryCreateActivity.INCOME);

        presenter.save("test", "");
        assertFalse(presenter.validateName("test"));
    }
}
