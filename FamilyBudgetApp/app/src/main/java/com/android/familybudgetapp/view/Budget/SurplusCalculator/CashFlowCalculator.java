package com.android.familybudgetapp.view.Budget.SurplusCalculator;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.ShowBudget.cashFlowType;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class used by *Date*Calculator
 */
public abstract class CashFlowCalculator implements SurplusCalculator {

    protected abstract boolean inDateRange(CashFlow cashFlow);

    protected abstract List<User> getUsers();

    protected abstract int getAmountForRange(CashFlow cashFlow);

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
     * @return list of cashFlows corresponding to the current month
     */
    protected List<CashFlow> getCashFlowOfType(cashFlowType type)
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
        for (User user: getUsers())
        {
            for(CashFlow item: user.getCashFlows())
            {
                if (item.getCategory().getClass() != classType)
                    continue;
                if (inDateRange(item))
                    newList.add(item);
            }
        }

        return newList;
    }

    /**
     * @param type enumeration of Income or Expense
     * @return list of pairs containing the category name and the amount corresponding to it
     */
    protected List<Tuples<String, Integer>> getCashFlowPerCategoryOfType(cashFlowType type)
    {

        Map<String, Integer> dict = new HashMap<>();
        List<Tuples<String, Integer>> tuplesList = new ArrayList<>();
        List<CashFlow> cashFlowList = getCashFlowOfType(type);
        for(CashFlow item: cashFlowList)
        {
            String name = item.getCategory().getName();
            dict.put(name, dict.getOrDefault(name, 0) + getAmountForRange(item));
        }
        for(Map.Entry<String, Integer> entry: dict.entrySet())
        {
            tuplesList.add(new Tuples<>(entry.getKey(), entry.getValue()));
        }
        return tuplesList;
    }
}
