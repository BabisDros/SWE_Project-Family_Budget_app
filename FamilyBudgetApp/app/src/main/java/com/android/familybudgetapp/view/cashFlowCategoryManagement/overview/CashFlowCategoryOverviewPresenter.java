package com.android.familybudgetapp.view.cashFlowCategoryManagement.overview;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class CashFlowCategoryOverviewPresenter extends BasePresenter<CashFlowCategoryOverviewView>
{
    public static final String DELETE_TITLE = "Delete Verification";
    public static final String DELETE_MSG = "Cash flow category: %s \n\nDo you want to delete it?";
    private FamilyDAO familyDAO;
    private UserDAO userDAO;
    private Family currentFamily;
    private CashFlowCategory currentCashFlowCategory;
    //cache categories at every session, because MAP does not guaranty order
    private List<CashFlowCategory> categories;

    /**
     * Sets the Family DAO.
     *
     * @param userDAO the {@link UserDAO} instance.
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * Sets the Family DAO.
     *
     * @param familyDAO the {@link FamilyDAO} instance.
     */
    public void setFamilyDAO(FamilyDAO familyDAO)
    {
        this.familyDAO = familyDAO;
    }

    /**
     * Retrieves the CashFlowCategory List for the current's user family.
     * if there are categories available, populates the RecyclerView .
     */
    public void searchCashFlowCategories()
    {
        User currentUser = userDAO.findByID(Initializer.currentUserID);
        currentFamily = currentUser.getFamily();

        categories = new ArrayList<>(currentFamily.getCashFlowCategories().values());
        if (!categories.isEmpty())
        {
            view.populateCategoriesRecyclerView(categories);
        }
    }

    /**
     * Show a verification message to confirm the deletion of a CashFlowCategory.
     *
     * @param category The CashFlowCategory to be deleted.
     */
    public void showVerification(CashFlowCategory category)
    {
        this.currentCashFlowCategory = category;
        view.showDeleteCategory(DELETE_TITLE, String.format(DELETE_MSG, category.getName()));
    }

    /**
     * Deletes the selected CashFlowCategory from the current family.
     * Updates the RecyclerView to reflect the change,
     * and saves the updated family data.
     */
    public void deleteCategory()
    {
        currentFamily.removeCashFlowCategory(currentCashFlowCategory);
        view.updateCategoriesRecyclerView(categories.indexOf(currentCashFlowCategory));
        familyDAO.save(currentFamily);
    }

    /**
     * Called when the homepage button is clicked.
     */
    public void navigateToHomepage()
    {
        view.goToHomepageActivity();
    }

    /**
     * Called when the AddMember FloatingActionButton is clicked.
     */
    public void navigateToCreateCashFlowCategory()
    {
        view.goToCreateCashFlowCategoryActivity();
    }
}
