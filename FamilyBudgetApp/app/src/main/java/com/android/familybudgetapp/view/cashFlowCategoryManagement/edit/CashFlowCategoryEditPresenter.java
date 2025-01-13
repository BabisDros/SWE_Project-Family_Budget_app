package com.android.familybudgetapp.view.cashFlowCategoryManagement.edit;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.BaseCashFlowManagementPresenter;


public class CashFlowCategoryEditPresenter extends BaseCashFlowManagementPresenter<CashFlowCategoryEditView>
{
    private String initialName;

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

    @Override
    public boolean validateNameUniqueness(String input)
    {
        if (currentUser.getFamily().getCashFlowCategories().get(inputLowerCase) != null && !initialName.equals(inputLowerCase))
        {
            view.showErrorMessage("Invalid name", "Name already exists");
            return false;
        }
        return true;
    }
}
