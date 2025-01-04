package com.android.familybudgetapp.view.Budget.CashFlowManager;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.InDateRange;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.ShowBudget.cashFlowType;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class monthlyManager extends CashFlowManager {

    @Override
    public List<Tuples<String, Integer>> CalculateAmountPerCategory(cashFlowType type) {
        return getCashFlowPerCategoryOfType(type);
    }

    protected boolean inDateRange(CashFlow cashFlow)
    {
        return InDateRange.cashFlowInMonthlyRange(cashFlow);
    }

    @Override
    protected int getAmountForRange(CashFlow cashFlow) {
        return cashFlow.getMonthlyAmount(YearMonth.now());
    }

}
