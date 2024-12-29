package com.android.familybudgetapp.view.Budget.ShowBudget;


import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.CashFlowManager.CashFlowManagerInterface;

import java.util.List;

public class BudgetPresenter {
    private BudgetView view;
    private UserDAO userDAO;
    private CashFlowManagerInterface cashFlowManager;
    private User currentUser;


    public void setView(BudgetView view)
    {
        this.view = view;
    }

    public void setUserDao(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setCashFlowManager(CashFlowManagerInterface cashFlowManagerInterface) {
        this.cashFlowManager = cashFlowManagerInterface;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public CashFlowManagerInterface getCashFlowManager(){
        return cashFlowManager;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<Tuples<String, Integer>> getExpensePerCategory()
    {
        return getCashFlowManager().CalculateAmountPerCategory(cashFlowType.Expense);
    }
    public List<Tuples<String, Integer>> getIncomePerCategory()
    {
        return getCashFlowManager().CalculateAmountPerCategory(cashFlowType.Income);
    }

    public int calculateSurplus()
    {
        return cashFlowManager.CalculateSurplus();
    }

}
