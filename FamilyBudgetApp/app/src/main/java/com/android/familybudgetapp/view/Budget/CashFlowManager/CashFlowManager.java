package com.android.familybudgetapp.view.Budget.CashFlowManager;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.ShowBudget.cashFlowType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class used by *Date*Calculator
 */
public abstract class CashFlowManager implements CashFlowManagerInterface {

    protected UserRetrievalStrategy userRetrievalStrategy;

    protected abstract boolean inDateRange(CashFlow cashFlow);

    protected List<User> getUsers() {
        return userRetrievalStrategy.getUsers();
    }

    protected abstract int getAmountForRange(CashFlow cashFlow);

    public void setUserRetrievalStrategy(UserRetrievalStrategy strategy)
    {
        userRetrievalStrategy = strategy;
    }

    public UserRetrievalStrategy getUserRetrievalStrategy()
    {
        return userRetrievalStrategy;
    }

    /**
     * @return Current surplus of CashFlows of range inDateRange
     */
    public int CalculateSurplus() {
        int total = 0;
        for (User user: getUsers())
        {
            for(CashFlow cashFlow: user.getCashFlows())
            {
                if (!inDateRange(cashFlow))
                    continue;
                if (cashFlow.getCategory() instanceof Income)
                    total+=getAmountForRange(cashFlow);
                else
                    total-=getAmountForRange(cashFlow);
            }
        }
        return total;
    }

    /**
     * @param type enumeration of Income or Expense
     * @return list of cashFlows per user corresponding to the current month
     */
    protected Map<Long, List<CashFlow>> getCashFlowOfType(cashFlowType type)
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
            case Both:
                classType = CashFlow.class;
                break;
            default:
                throw new IllegalArgumentException("Illegal enumeration value");
        }

        Map<Long, List<CashFlow>> newList = new HashMap<>();
        for (User user: getUsers())
        {
            List<CashFlow> cashFlowsOfUser = new ArrayList<>();
            for(CashFlow item: user.getCashFlows())
            {
                if (item.getCategory().getClass() != classType && classType != CashFlow.class)
                    continue;
                if (inDateRange(item))
                    cashFlowsOfUser.add(item);
            }
            newList.put(user.getID(), cashFlowsOfUser);
        }

        return newList;
    }

    /**
     * @return Map of list of {@link Expense} per user in the object's date range
     */
    public Map<Long, List<CashFlow>> getExpense()
    {
        return getCashFlowOfType(cashFlowType.Expense);
    }

    /**
     * @return Map of list of {@link Income} per user in the object's date range
     */
    public Map<Long, List<CashFlow>> getIncome()
    {
        return getCashFlowOfType(cashFlowType.Income);
    }

    /**
     * @return Map of list of every {@link CashFlow} per user in the object's date range
     */
    public Map<Long, List<CashFlow>> getExpenseAndIncome()
    {
        return getCashFlowOfType(cashFlowType.Both);
    }

    /**
     * @param type enumeration of Income or Expense
     * @return list of pairs containing the category name and the amount corresponding to it
     */
    protected List<Tuples<String, Integer>> getCashFlowPerCategoryOfType(cashFlowType type)
    {

        Map<String, Integer> dict = new HashMap<>();
        List<Tuples<String, Integer>> tuplesList = new ArrayList<>();
        Map<Long, List<CashFlow>> cashFlowList = getCashFlowOfType(type);
        for (List<CashFlow> innerList: cashFlowList.values())
        {
            for(CashFlow item: innerList)
            {
                String name = item.getCategory().getName();
                dict.put(name, dict.getOrDefault(name, 0) + getAmountForRange(item));
            }
        }

        for(Map.Entry<String, Integer> entry: dict.entrySet())
        {
            tuplesList.add(new Tuples<>(entry.getKey(), entry.getValue()));
        }
        return tuplesList;
    }

    /**
     * @param cashFlow CashFlow for which you want the amount of the object's date range
     * @return Amount
     */
    public int getAmount(CashFlow cashFlow)
    {
        return getAmountForRange(cashFlow);
    }
}
