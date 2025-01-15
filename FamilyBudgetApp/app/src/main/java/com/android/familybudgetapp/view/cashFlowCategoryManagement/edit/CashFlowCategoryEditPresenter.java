package com.android.familybudgetapp.view.cashFlowCategoryManagement.edit;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.BaseCashFlowManagementPresenter;


public class CashFlowCategoryEditPresenter extends BaseCashFlowManagementPresenter<CashFlowCategoryEditView>
{
    private String initialName;

    /**
     * Gets the CashFlowFategory from the current user's family and populates
     * the view fields with the category data, including the limit if it's an Expense type.
     *
     * @param name The name of the CashFlowFategory.
     */
    public void setCashFlowCategoryData(String name)
    {
        currentUser = userDAO.findByID(Initializer.currentUserID);
        initialName = name.toLowerCase();
        currentFamily = currentUser.getFamily();
        CashFlowCategory categoryToEdit = currentFamily.getCashFlowCategories().get(initialName);
        view.setNameField(name);

        if (categoryToEdit != null && categoryToEdit.getClass() == Expense.class)
        {
            Expense expense = (Expense) categoryToEdit;
            view.setLimitField(String.valueOf(expense.getLimit()));
            view.setSpinnerToExpense();
        }
    }

    /**
     * Saves the edited CashFlowFategory with the provided name and limit.
     * Validates the name, creates the new category, removes the existing category,
     * and adds the new one. Saves the updated family data and calls the Activity
     * to change to the CashFlowCategoryOverview.
     *
     * @param name The name of the CashFlowFategory.
     * @param limit The limit for the CashFlowFategory (used for EXPENSE type).
     */
    public void save(String name, String limit)
    {
        if (!validateName(name)) return;

        CashFlowCategory newCategory = createCashFlow(name, limit);
        if (newCategory == null) return;

        CashFlowCategory existing = currentFamily.getCashFlowCategories().get(initialName);
        currentFamily.removeCashFlowCategory(existing);
        currentFamily.addCashFlowCategory(newCategory);

        familyDAO.save(currentFamily);
        view.goToCashFlowCategoryOverview();
    }

    /**
     * Validates the uniqueness of the provided name.
     * Checks if a CashFlowFategory with the same name already exists but its
     * not the same as it's initial name. That allows to save with the same name.
     *
     * @param input The name to be checked for uniqueness.
     * @return true if the name exists but does not matches the initial name, false otherwise.
     */
    @Override
    protected boolean validateNameUniqueness(String input)
    {
        if (currentUser.getFamily().getCashFlowCategories().get(inputLowerCase) != null && !initialName.equals(inputLowerCase))
        {
            view.showErrorMessage("Invalid name", "Name already exists");
            return false;
        }
        return true;
    }
}
