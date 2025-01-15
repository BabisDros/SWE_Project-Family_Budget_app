package com.android.familybudgetapp.view.budget.cashFlowManager;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.budget.showBudget.cashFlowType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class used by *Date*Manager
 */
public abstract class CashFlowManager implements CashFlowManagerInterface {

    protected UserRetrievalStrategy userRetrievalStrategy;

    /**
     * Determines if the given {@link CashFlow} is within the specified date range of current strategy.
     *
     * @param cashFlow the {@link CashFlow} instance to evaluate
     * @return true if the {@link CashFlow} falls within the specified date range, false otherwise
     */
    protected abstract boolean inDateRange(CashFlow cashFlow);

    /**
     * Retrieves a list of users based on the configured {@link UserRetrievalStrategy}.
     *
     * @return A list of {@link User} objects as determined by the user retrieval strategy.
     */
    protected List<User> getUsers() {
        return userRetrievalStrategy.getUsers();
    }

    /**
     * Retrieves the monthly amount for the specified {@link CashFlow} based on the current date range strategy.
     *
     * @param cashFlow the {@link CashFlow} instance from which the monthly amount is to be calculated
     * @return the monthly amount for the specified {@link CashFlow}
     */
    protected abstract int getAmountForRange(CashFlow cashFlow);

    /**
     * Sets the strategy to be used for retrieving users.
     *
     * @param strategy The {@link UserRetrievalStrategy} to be configured for retrieving users.
     */
    public void setUserRetrievalStrategy(UserRetrievalStrategy strategy)
    {
        userRetrievalStrategy = strategy;
    }

    /**
     * Retrieves the current {@link UserRetrievalStrategy} being used
     *
     * @return The {@link UserRetrievalStrategy} currently configured in the system.
     */
    public UserRetrievalStrategy getUserRetrievalStrategy()
    {
        return userRetrievalStrategy;
    }

    /**
     * Calculates the total surplus by iterating through all users and their respective {@link CashFlow}s.
     * The method determines whether each {@link CashFlow} is within the specified date range and performs
     * either addition or subtraction to the total based on its category ({@link Income} or {@link Expense}).
     *
     * @return The calculated surplus as an integer, representing the total aggregated difference
     *         between {@link Income} and {@link Expense}s for all users within the specified date range.
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
     * Retrieves a map of cashFlows of the specified type (e.g., income, expense, or both)
     * for each user. The method filters and groups {@link CashFlow}s based on their type,
     * the configured user retrieval strategy, and whether they fall within a defined date range.
     *
     * @param type The type of {@link CashFlow} to retrieve. Possible values include
     *             {@link cashFlowType#Income}, {@link cashFlowType#Expense}, or {@link cashFlowType#Both}.
     * @return A map where the key is the user ID and the value is a list of {@link CashFlow}s
     *         of the specified type that belong to that user.
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
     * Retrieves a map of {@link CashFlow} lists categorized as expenses for each user.
     * The method filters and groups expenses from the {@link CashFlow}s of all users
     * based on the configured user retrieval strategy and the defined date range.
     *
     * @return A map where the key is the user ID and the value is a list of
     *         {@link Expense} objects categorized as expenses for that user.
     */
    public Map<Long, List<CashFlow>> getExpense()
    {
        return getCashFlowOfType(cashFlowType.Expense);
    }

    /**
     * Retrieves a map of {@link CashFlow} lists categorized as income for each user.
     * This method filters and groups income {@link CashFlow}s for all users based on
     * the configured user retrieval strategy and the defined date range.
     *
     * @return A map where the key is the user ID and the value is a list of
     *         {@link Income} objects categorized as income for that user.
     */
    public Map<Long, List<CashFlow>> getIncome()
    {
        return getCashFlowOfType(cashFlowType.Income);
    }

    /**
     * Retrieves a map of {@link CashFlow}s categorized as both expenses and income for each user.
     * This method utilizes the user retrieval strategy and filters the {@link CashFlow}s
     * based on the defined date range, aggregating both income and expenses.
     *
     * @return A map where the key is the user ID and the value is a list of {@link CashFlow}
     *         objects categorized as both expenses and income for that user.
     */
    public Map<Long, List<CashFlow>> getExpenseAndIncome()
    {
        return getCashFlowOfType(cashFlowType.Both);
    }

    /**
     * Aggregates {@link CashFlow}s of a given type (e.g., income or expense) into a list of categories
     * and their corresponding total amounts. The categories are derived from each {@link CashFlow}'s category
     * name, and the total amounts are computed based on the {@link CashFlow}s within the specified range.
     *
     * @param type The type of {@link CashFlow} to process (e.g., {@link cashFlowType#Income},
     *         {@link cashFlowType#Expense}, or {@link cashFlowType#Both}).
     * @return A list of tuples, where each tuple contains a category name (String) and the total
     *         aggregated amount (Integer) for that category.
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
     * Retrieves the amount associated with a given CashFlow for the strategy's range.
     *
     * @param cashFlow The CashFlow object for which the amount is being retrieved.
     * @return The amount related to the specified CashFlow.
     */
    public int getAmount(CashFlow cashFlow)
    {
        return getAmountForRange(cashFlow);
    }
}
