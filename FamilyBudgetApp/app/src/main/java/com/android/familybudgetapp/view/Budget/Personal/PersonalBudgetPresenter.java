package com.android.familybudgetapp.view.Budget.Personal;


import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.InDateRange;
import com.android.familybudgetapp.utilities.Tuples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalBudgetPresenter{
    private PersonalBudgetView view;
    private UserDAO userDAO;

    private User currentUser;


    public void setView(PersonalBudgetView view)
    {
        this.view = view;
    }

    public void setUserDao(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * @param type enumeration of Income or Expense
     * @return list of cashFlows corresponding to the current month
     */
    private List<CashFlow> getUserCashFlowOfType(cashFlowType type)
    {
        Object classType;
        switch (type)
        {
            case Expense:
                classType = Expense.class;
                break;
            case Income:
                classType = Income.class;
                break;
            default:
                throw new IllegalArgumentException("Illegal enumeration value");
        }

        List<CashFlow> newList = new ArrayList<>();
        for(CashFlow item: currentUser.getCashFlows())
        {
            if (item.getCategory().getClass() != classType)
                continue;
            if (InDateRange.cashFlowInMonthlyRange(item))
                newList.add(item);
        }
        return newList;
    }

    /**
     * @param type enumeration of Income or Expense
     * @return list of pairs containing the category name and the amount corresponding to it
     */
    private List<Tuples<String, Integer>> getUserCashFlowPerCategoryOfType(cashFlowType type)
    {

        Map<String, Integer> dict = new HashMap<>();
        List<Tuples<String, Integer>> tuplesList = new ArrayList<>();
        List<CashFlow> cashFlowList = getUserCashFlowOfType(type);
        for(CashFlow item: cashFlowList)
        {
            String name = item.getCategory().getName();
            dict.put(name, dict.getOrDefault(name, 0) + item.getMonthlyAmount());
        }
        for(Map.Entry<String, Integer> entry: dict.entrySet())
        {
            tuplesList.add(new Tuples<>(entry.getKey(), entry.getValue()));
        }
        return tuplesList;
    }

    public List<Tuples<String, Integer>> getUserExpensePerCategory()
    {
        return getUserCashFlowPerCategoryOfType(cashFlowType.Expense);
    }
    public List<Tuples<String, Integer>> getUserIncomePerCategory()
    {
        return getUserCashFlowPerCategoryOfType(cashFlowType.Income);
    }

    private enum cashFlowType
    {
        Income,
        Expense
    }

}
