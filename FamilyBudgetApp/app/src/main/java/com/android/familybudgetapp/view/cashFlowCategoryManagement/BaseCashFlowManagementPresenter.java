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

    /**
     * Validates the provided name input.
     * Checks if the input is alphanumeric and contains spaces, then validates its uniqueness.
     *
     * @param input The name input to be validated.
     * @return true if the name is valid and unique, false otherwise.
     */
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

    /**
     * Abstract method to validate the uniqueness of the provided name input.
     * Subclasses must implement this method to define how the uniqueness is checked.
     *
     * @param input The name input to be checked for uniqueness.
     * @return true if the name is unique, false otherwise.
     */
    protected abstract boolean validateNameUniqueness(String input);

    /**
     * Validates the provided limit input.
     * Checks if the input is empty and if the parsed integer is greater than zero.
     *
     * @param limit The limit input to be validated.
     * @return The parsed limit if valid, -1 if invalid.
     */
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

    /**
     * Creates a new CashFlowCategory with the provided name and limit.
     * If the type is EXPENSE, it validates the limit and creates an Expense object.
     * Else it creates an Income object.
     *
     * @param name The name of the new cash flow category.
     * @param limit The limit for the category (only used for EXPENSE type).
     * @return A new CashFlowCategory object, or null if the type is Expense and limit is invalid.
     */
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
    /**
     * Sets the type of the cash flow category.Expense or Income.
     *
     * @param cashFlowCategory The type of the cash flow category.
     */
    public void setType(String cashFlowCategory)
    {
        currentType = cashFlowCategory;
    }
}
