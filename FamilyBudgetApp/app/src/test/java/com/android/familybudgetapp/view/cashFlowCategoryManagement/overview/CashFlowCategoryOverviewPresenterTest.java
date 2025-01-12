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

    @Test
    public void correctSetUserDAO() throws NoSuchFieldException, IllegalAccessException
    {
        //temporary access private field userDAO
        Field userDAOField = CashFlowCategoryOverviewPresenter.class.getDeclaredField("userDAO");
        userDAOField.setAccessible(true);
        UserDAO userDAO = (UserDAO) userDAOField.get(presenter);

        assertEquals(this.userDAO, userDAO);
    }

    @Test
    public void correctSetFamilyDAO() throws NoSuchFieldException, IllegalAccessException
    {
        //temporary access private field userDAO
        Field familyDAOField = CashFlowCategoryOverviewPresenter.class.getDeclaredField("familyDAO");
        familyDAOField.setAccessible(true);
        FamilyDAO familyDAO = (FamilyDAO) familyDAOField.get(presenter);

        assertEquals(this.familyDAO, familyDAO);
    }

    @Test
    public void searchNotEmptyCashFlowCategories()
    {
        presenter.searchCashFlowCategories();
        assertEquals(new ArrayList<>(currentFamily.getCashFlowCategories().values()), viewStub.getCashFlowCategories());
    }

    @Test
    public void searchEmptyCashFlowCategories()
    {
        currentUser = userDAO.findByUsername("Test4");
        Initializer.currentUserID = currentUser.getID();

        currentFamily = currentUser.getFamily();

        presenter.searchCashFlowCategories();
        assertEquals(new ArrayList<>(currentFamily.getCashFlowCategories().values()), viewStub.getCashFlowCategories());
    }

    @Test
    public void showVerification()
    {
        String currentCategoryName = "Food".toLowerCase();
        CashFlowCategory currentCategory = currentFamily.getCashFlowCategories().get(currentCategoryName);

        assertNotNull(currentCategory);
        presenter.showVerification(currentCategory);

        assertEquals(String.format(presenter.DELETE_MSG, currentCategory.getName()), viewStub.getMsg());
        assertEquals(presenter.DELETE_TITLE, viewStub.getTitle());
    }

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


    @Test
    public void navigateToHomepage()
    {
        presenter.navigateToHomepage();
        assertEquals(1, viewStub.getHomepageActivityCounter());
    }

    @Test
    public void navigateToCreateCashFlowCategory()
    {
        presenter.navigateToCreateCashFlowCategory();
        assertEquals(1, viewStub.getCashFlowActivityCounter());
    }
}
