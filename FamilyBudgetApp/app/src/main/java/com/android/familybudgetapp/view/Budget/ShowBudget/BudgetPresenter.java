package com.android.familybudgetapp.view.Budget.ShowBudget;


import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.domain.recurPeriod;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.CashFlowManager.CashFlowManagerInterface;

import java.time.LocalDateTime;
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

    public String addCashFlow(String categoryName, String cashFlowValue,
                              boolean isRecurring, LocalDateTime dateStart, LocalDateTime dateEnd,
                              int recurrencePeriodIdx) {

        // category
        if (categoryName.isEmpty()) {
            return "Please select a category.";
        }
        CashFlowCategory category = getCurrentUser().getFamily().getCashFlowCategories().get(categoryName);

        // amount
        if (cashFlowValue.isEmpty() || cashFlowValue.startsWith(".") ||
                cashFlowValue.endsWith(".") || cashFlowValue.equals("0")) {
            return "Please enter a valid amount.";
        }
        double euroValue = Double.parseDouble(cashFlowValue);
        int centsValue = (int) (euroValue * 100);

        // date start
        if (!CashFlow.validateDateStart(dateStart)) {
            return "Invalid start date";
        }

        // recurrence period
        recurPeriod period = recurPeriod.values()[recurrencePeriodIdx];

        CashFlow newCashFlow;

        if (isRecurring) {
            // date end
            if (!Repeating.validateDateEnd(dateStart, dateEnd)) {
                return "Invalid end date";
            }
            newCashFlow = new Repeating(centsValue, category, dateStart, dateEnd, period);
        } else {
            newCashFlow = new OneOff(centsValue, category, dateStart);
        }

        currentUser.addCashFlow(newCashFlow);
        return "0";
    }
}
