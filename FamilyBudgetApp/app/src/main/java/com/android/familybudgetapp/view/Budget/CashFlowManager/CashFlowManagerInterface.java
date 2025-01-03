package com.android.familybudgetapp.view.Budget.CashFlowManager;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.ShowBudget.cashFlowType;

import java.util.List;
import java.util.Map;


public interface CashFlowManagerInterface {

    int CalculateSurplus();

    void setUserRetrievalStrategy(UserRetrievalStrategy strategy);

    List<Tuples<String, Integer>> CalculateAmountPerCategory(cashFlowType type);

    Map<Long, List<CashFlow>> getExpense();

    Map<Long, List<CashFlow>> getIncome();

    Map<Long, List<CashFlow>> getExpenseAndIncome();

    int getAmount(CashFlow cashFlow);
}
