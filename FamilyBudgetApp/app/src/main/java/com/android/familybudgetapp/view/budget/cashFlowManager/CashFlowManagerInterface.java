package com.android.familybudgetapp.view.budget.cashFlowManager;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.budget.showBudget.cashFlowType;

import java.util.List;
import java.util.Map;


public interface CashFlowManagerInterface {

    /**
     * Calculates the total surplus by iterating through all users and their respective {@link CashFlow}s.
     * The method determines whether each {@link CashFlow} is within the specified date range and performs
     * either addition or subtraction to the total based on its category ({@link Income} or {@link Expense}).
     *
     * @return The calculated surplus as an integer, representing the total aggregated difference
     *         between {@link Income} and {@link Expense}s for all users within the specified date range.
     */
    int CalculateSurplus();

    /**
     * Sets the strategy to be used for retrieving users.
     *
     * @param strategy The {@link UserRetrievalStrategy} to be configured for retrieving users.
     */
    void setUserRetrievalStrategy(UserRetrievalStrategy strategy);

    /**
     * Retrieves the current {@link UserRetrievalStrategy} being used
     *
     * @return The {@link UserRetrievalStrategy} currently configured in the system.
     */
    UserRetrievalStrategy getUserRetrievalStrategy();

    List<Tuples<String, Integer>> CalculateAmountPerCategory(cashFlowType type);

    /**
     * Retrieves a map of {@link CashFlow} lists categorized as expenses for each user.
     * The method filters and groups expenses from the {@link CashFlow}s of all users
     * based on the configured user retrieval strategy and the defined date range.
     *
     * @return A map where the key is the user ID and the value is a list of
     *         {@link Expense} objects categorized as expenses for that user.
     */
    Map<Long, List<CashFlow>> getExpense();

    /**
     * Retrieves a map of {@link CashFlow} lists categorized as income for each user.
     * This method filters and groups income {@link CashFlow}s for all users based on
     * the configured user retrieval strategy and the defined date range.
     *
     * @return A map where the key is the user ID and the value is a list of
     *         {@link Income} objects categorized as income for that user.
     */
    Map<Long, List<CashFlow>> getIncome();

    /**
     * Retrieves a map of {@link CashFlow}s categorized as both expenses and income for each user.
     * This method utilizes the user retrieval strategy and filters the {@link CashFlow}s
     * based on the defined date range, aggregating both income and expenses.
     *
     * @return A map where the key is the user ID and the value is a list of {@link CashFlow}
     *         objects categorized as both expenses and income for that user.
     */
    Map<Long, List<CashFlow>> getExpenseAndIncome();

    /**
     * Retrieves the amount associated with a given {@link CashFlow} for the strategy's range.
     *
     * @param cashFlow The CashFlow object for which the amount is being retrieved.
     * @return The amount related to the specified CashFlow.
     */
    int getAmount(CashFlow cashFlow);
}
