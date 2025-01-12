package com.android.familybudgetapp.view.cashFlowCategoryManagement;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.CommonStringValidations;
import com.android.familybudgetapp.view.base.BasePresenter;
import com.android.familybudgetapp.view.base.BaseView;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.create.CashFlowCategoryCreateActivity;

public abstract class BaseCashFlowManagementPresenter<V extends BaseView> extends BasePresenter<V>
{
    protected UserDAO userDAO;

    protected FamilyDAO familyDAO;
    protected User currentUser;
    protected Family currentFamily;
    protected String currentType;
    protected String inputLowerCase;

    /**
     * Sets the User DAO.
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

    public boolean validateName(String input)
    {
        inputLowerCase = input.toLowerCase();

        if (!CommonStringValidations.isAlphanumericWithSpaces(inputLowerCase))
        {
            view.showErrorMessage("Invalid name", CommonStringValidations.INVALID_ALPHANUMERICAL);
            return false;
        }

        return validateNameUniqueness(inputLowerCase);
    }

    public abstract boolean validateNameUniqueness(String input);

    public int validateLimit(String limit)
    {
        if (limit.isEmpty())
        {
            view.showErrorMessage("Invalid limit", "Please enter a limit");
            return -1;
        }
        int parsedLimit = Integer.parseInt(limit);
        if (!Expense.isLimitValid(parsedLimit))
        {
            view.showErrorMessage("Invalid limit", "Limit should be greater than zero");
            return -1;
        }
        return parsedLimit;
    }

    public CashFlowCategory createCashFlow(String name, String limit)
    {
        CashFlowCategory newCategory;
        if (currentType.equals(CashFlowCategoryCreateActivity.EXPENSE))
        {
            int parsedLimit = validateLimit(limit);
            if (parsedLimit == -1) return null;
            newCategory = new Expense(name, parsedLimit);
        }
        else
        {
            newCategory = new Income(name);
        }

        return newCategory;
    }

    public void setType(String cashFlowCategory)
    {
        currentType = cashFlowCategory;
    }
}
