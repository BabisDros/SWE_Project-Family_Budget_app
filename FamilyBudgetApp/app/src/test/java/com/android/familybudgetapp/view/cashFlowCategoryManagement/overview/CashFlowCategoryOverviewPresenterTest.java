package com.android.familybudgetapp.view.cashFlowCategoryManagement.overview;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.memorydao.UserDAOMemory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CashFlowCategoryOverviewPresenterTest
{
    private CashFlowCategoryOverviewPresenter presenter;
    private CashFlowCategoryOverviewViewStub viewStub;
    private Family currentFamily;
    private User currentUser;
    private UserDAO userDAO;
    private FamilyDAO familyDAO;

    @Before
    @Test
    public void setUp()
    {
        new MemoryInitializer().prepareData();
        presenter = new CashFlowCategoryOverviewPresenter();
        viewStub = new CashFlowCategoryOverviewViewStub();

        userDAO = new UserDAOMemory();
        familyDAO = new FamilyDAOMemory();

        presenter.setView(viewStub);
        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);

        currentUser = userDAO.findByUsername("usernameTest");
        Initializer.currentUserID = currentUser.getID();

        currentFamily = currentUser.getFamily();
    }

    /**
     * Tests if the UserDAO is correctly set in the CashFlowCategoryOverviewPresenter class
     * by accessing the private userDAO field and asserting that it matches the provided userDAO.
     */
    @Test
    public void correctSetUserDAO() throws NoSuchFieldException, IllegalAccessException
    {
        //temporary access private field userDAO
        Field userDAOField = CashFlowCategoryOverviewPresenter.class.getDeclaredField("userDAO");
        userDAOField.setAccessible(true);
        UserDAO userDAO = (UserDAO) userDAOField.get(presenter);

        assertEquals(this.userDAO, userDAO);
    }

    /**
     * Tests if the familyDAO is correctly set in the CashFlowCategoryOverviewPresenter class
     * by accessing the private userDAO field and asserting that it matches the provided familyDAO.
     */
    @Test
    public void correctSetFamilyDAO() throws NoSuchFieldException, IllegalAccessException
    {
        //temporary access private field familyDAO
        Field familyDAOField = CashFlowCategoryOverviewPresenter.class.getDeclaredField("familyDAO");
        familyDAOField.setAccessible(true);
        FamilyDAO familyDAO = (FamilyDAO) familyDAOField.get(presenter);

        assertEquals(this.familyDAO, familyDAO);
    }

    /**
     * Tests if the searchCashFlowCategories method in the CashFlowCategoryOverviewPresenter class
     * correctly retrieves and calls populateCategoriesRecyclerView method in view by asserting the two lists are equal.
     * (The family in the setup has non-empty CashFlowCategories).
     */
    @Test
    public void searchNotEmptyCashFlowCategories()
    {
        presenter.searchCashFlowCategories();
        assertEquals(new ArrayList<>(currentFamily.getCashFlowCategories().values()), viewStub.getCashFlowCategories());
    }

    /**
     * Tests if the searchCashFlowCategories method in the CashFlowCategoryOverviewPresenter class
     * correctly retrieves the list of empty cashFlowCategories from the current family and asserts equality with
     * the initialized empty list in view stub.
     * (The family of user Test4 (set in Initializer) has empty CashFlowCategories).
     */
    @Test
    public void searchEmptyCashFlowCategories()
    {
        currentUser = userDAO.findByUsername("Test4");
        Initializer.currentUserID = currentUser.getID();

        currentFamily = currentUser.getFamily();

        presenter.searchCashFlowCategories();
        assertEquals(new ArrayList<>(currentFamily.getCashFlowCategories().values()), viewStub.getCashFlowCategories());
    }

    /**
     * Tests if the showVerification method in the CashFlowCategoryOverviewPresenter class
     * correctly shows the delete verification message and title when a cashFlowCategory is selected for deletion.
     * The currentCategoryName 'Food' used, is set in Initializer.
     */
    @Test
    public void showDeleteVerification()
    {
        String currentCategoryName = "Food".toLowerCase();
        CashFlowCategory currentCategory = currentFamily.getCashFlowCategories().get(currentCategoryName);

        assertNotNull(currentCategory);
        presenter.showVerification(currentCategory);

        assertEquals(String.format(presenter.DELETE_MSG, currentCategory.getName()), viewStub.getMsg());
        assertEquals(presenter.DELETE_TITLE, viewStub.getTitle());
    }

    /**
     * Tests if the deleteCategory method in the CashFlowCategoryOverviewPresenter class
     * correctly deletes an existing cashFlowCategory by asserting that the category is removed
     * from the current family and that the correct index to delete is passed to the view.
     */
    @Test
    public void deleteExistingCategory()
    {
        String currentCategoryName = "Food".toLowerCase();
        CashFlowCategory currentCategory = currentFamily.getCashFlowCategories().get(currentCategoryName);
        assertNotNull(currentCategory);

        presenter.searchCashFlowCategories();
        int currentIndexToDelete = viewStub.getCashFlowCategories().indexOf(currentCategory);

        presenter.showVerification(currentCategory);
        presenter.deleteCategory();

        assertNull(currentFamily.getCashFlowCategories().get(currentCategoryName));
        assertEquals(currentIndexToDelete, viewStub.getIndexToDelete());
    }


    /**
     * Tests if the navigateToHomepage method in the CashFlowCategoryOverviewPresenter class
     * correctly calls the goToHomepageActivity in the view stub by counting the numbers of calls.
     */
    @Test
    public void navigateToHomepage()
    {
        presenter.navigateToHomepage();
        assertEquals(1, viewStub.getHomepageActivityCounter());
    }

    /**
     * Tests if the navigateToCreateCashFlowCategory method in the CashFlowCategoryOverviewPresenter class
     * correctly calls the goToCreateCashFlowCategoryActivity in the view stub by counting the numbers of calls.
     */
    @Test
    public void navigateToCreateCashFlowCategory()
    {
        presenter.navigateToCreateCashFlowCategory();
        assertEquals(1, viewStub.getCashFlowActivityCounter());
    }
}
