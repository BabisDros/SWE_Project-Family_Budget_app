package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;


import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.BaseCashFlowManagementPresenter;

public class CashFlowCategoryCreatePresenter extends BaseCashFlowManagementPresenter<CashFlowCategoryCreateView>
{
    public static final String SUCCESSFUL_ADDED_CATEGORY_TITLE = "Category: %s added!";
    public static final String ADD_EXTRA_CATEGORY_PROMPT = "Do you want to add a new category?";

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

    public void resetFields()
    {
        view.clearFields();
    }

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
