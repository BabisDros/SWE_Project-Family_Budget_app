package com.android.familybudgetapp.view.Budget.ShowBudget;


import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.InDateRange;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.SurplusCalculator.SurplusCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetPresenter {
    private BudgetView view;
    private UserDAO userDAO;
    private SurplusCalculator surplusCalculator;
    private User currentUser;


    public void setView(BudgetView view)
    {
        this.view = view;
    }

    public void setUserDao(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setSurplusCalculator(SurplusCalculator surplusCalculator) {
        this.surplusCalculator = surplusCalculator;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public SurplusCalculator getSurplusCalculator(){
        return surplusCalculator;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<Tuples<String, Integer>> getExpensePerCategory()
    {
        return getSurplusCalculator().CalculateAmountPerCategory(cashFlowType.Expense);
    }
    public List<Tuples<String, Integer>> getIncomePerCategory()
    {
        return getSurplusCalculator().CalculateAmountPerCategory(cashFlowType.Income);
    }

    public int calculateSurplus()
    {
        return surplusCalculator.CalculateSurplus();
    }

}
