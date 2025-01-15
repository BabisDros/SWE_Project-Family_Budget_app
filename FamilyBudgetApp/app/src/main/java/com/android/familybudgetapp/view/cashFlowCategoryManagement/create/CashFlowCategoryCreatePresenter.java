package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;


import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.BaseCashFlowManagementPresenter;

public class CashFlowCategoryCreatePresenter extends BaseCashFlowManagementPresenter<CashFlowCategoryCreateView>
{
    public static final String SUCCESSFUL_ADDED_CATEGORY_TITLE = "Category: %s added!";
    public static final String ADD_EXTRA_CATEGORY_PROMPT = "Do you want to add a new category?";

    /**
     * Saves a new CashFlowFategory with the provided name and limit.
     * Validates the name and creates the CashFlowFategory, then adds it to the user's family.
     * Saves the updated family and shows a success message and prompt to add a new one.
     *
     * @param name The name of the CashFlowFategory.
     * @param limit The limit for the CashFlowFategory (used for EXPENSE type).
     */
    public void save(String name, String limit)
    {
        currentUser = userDAO.findByID(Initializer.currentUserID);
        if (!validateName(name)) return;

        CashFlowCategory newCategory = createCashFlow(name, limit);
        if (newCategory == null) return;

        currentFamily = currentUser.getFamily();
        currentFamily.addCashFlowCategory(newCategory);
        familyDAO.save(currentFamily);
        view.showAddCategoryMsg(String.format(SUCCESSFUL_ADDED_CATEGORY_TITLE, name), ADD_EXTRA_CATEGORY_PROMPT);
    }

    /**
     * Clears the fields.
     */
    public void resetFields()
    {
        view.clearFields();
    }

    /**
     * Validates the uniqueness of the provided category name.
     * Checks if a CashFlowFategory with the same name already exists in the family.
     *
     * @param input The name to be checked for uniqueness.
     * @return true if the name is unique, false if the name already exists.
     */
    @Override
    protected boolean validateNameUniqueness(String input)
    {
        if (currentUser.getFamily().getCashFlowCategories().get(inputLowerCase) != null)
        {
            view.showErrorMessage("Invalid name", "Name already exists");
            return false;
        }
        return true;
    }
}
