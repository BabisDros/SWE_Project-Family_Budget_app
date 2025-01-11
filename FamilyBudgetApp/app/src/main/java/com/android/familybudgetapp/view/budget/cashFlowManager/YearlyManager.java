package com.android.familybudgetapp.view.budget.cashFlowManager;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.utilities.InDateRange;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.budget.showBudget.cashFlowType;

import java.time.YearMonth;
import java.util.List;

public class YearlyManager extends CashFlowManager {

    @Override
    public List<Tuples<String, Integer>> CalculateAmountPerCategory(cashFlowType type) {
        return getCashFlowPerCategoryOfType(type);
    }

    @Override
    protected boolean inDateRange(CashFlow cashFlow) {
        return InDateRange.cashFlowInYearlyRange(cashFlow);
    }

    @Override
    protected int getAmountForRange(CashFlow cashFlow) {
        return cashFlow.getYearlyAmount(YearMonth.now());
    }

}
