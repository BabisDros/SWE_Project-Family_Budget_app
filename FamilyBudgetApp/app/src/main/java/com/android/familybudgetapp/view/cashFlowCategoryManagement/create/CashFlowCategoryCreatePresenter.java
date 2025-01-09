package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;


import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.BaseCashFlowManagementPresenter;

public class CashFlowCategoryCreatePresenter extends BaseCashFlowManagementPresenter<CashFlowCategoryCreateView>
{
    protected static final String SUCCESSFUL_ADDED_CATEGORY_MSG = "Category: %s added!";
    protected static final String ADD_EXTRA_CATEGORY_PROMPT = "Do you want to add a new category?";


    public void save(String name, String limit)
    {
        currentUser = userDAO.findByID(Initializer.currentUserID);
        if (!validateName(name)) return;
        CashFlowCategory newCategory;
        if (currentType.equals(CashFlowCategoryCreateActivity.EXPENSE))
        {
            int parsedLimit = validateLimit(limit);
            if (parsedLimit == -1) return;
            newCategory = new Expense(name, parsedLimit);
        }
        else
        {
            newCategory = new Income(name);
        }


        currentUser.getFamily().addCashFlowCategory(newCategory);
        view.showAddCategoryMsg(String.format(SUCCESSFUL_ADDED_CATEGORY_MSG, name) ,ADD_EXTRA_CATEGORY_PROMPT);
    }

    public void resetFields()
    {
        view.clearFields();
    }

    @Override
    public boolean validateNameUniqueness(String input)
    {
        if (currentUser.getFamily().getCashFlowCategories().get(inputLowerCase) != null)
        {
            view.showErrorMessage("Invalid name", "Name already exists");
            return false;
        }
        return true;
    }
}
