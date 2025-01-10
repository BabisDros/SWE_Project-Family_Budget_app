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
    FamilyDAO familyDAO;
    UserDAO userDAO;
    Family currentFamily;

    //cache categories at every session, because MAP does not guaranty order
    List<CashFlowCategory> categories;

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

    public void deleteCategory(CashFlowCategory category)
    {
        if (category != null)
        {
            currentFamily.removeCashFlowCategory(category);
            view.updateCategoriesRecyclerView(categories.indexOf(category));
            familyDAO.save(currentFamily);
        }
    }

    public void navigateToHomepage()
    {
        view.goToHomepageActivity();
    }

    public void navigateToCreateCashFlowCategory()
    {
        view.goToCreateCashFlowCategoryActivity();
    }
}
