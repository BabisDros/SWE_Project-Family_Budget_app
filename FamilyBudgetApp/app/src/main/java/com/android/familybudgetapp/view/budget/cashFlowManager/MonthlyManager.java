package com.android.familybudgetapp.view.budget.cashFlowManager;

import android.util.Log;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.MonthlySurplus;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.InDateRange;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.budget.showBudget.cashFlowType;

import java.time.YearMonth;
import java.util.List;

public class MonthlyManager extends CashFlowManager {

    @Override
    public List<Tuples<String, Integer>> CalculateAmountPerCategory(cashFlowType type) {
        return getCashFlowPerCategoryOfType(type);
    }

    @Override
    protected boolean inDateRange(CashFlow cashFlow)
    {
        return InDateRange.cashFlowInMonthlyRange(cashFlow);
    }

    @Override
    protected int getAmountForRange(CashFlow cashFlow) {
        return cashFlow.getMonthlyAmount(YearMonth.now());
    }

    public void moveUnallocatedSurplusToSavings(Family family) {
        // Users are only allowed to allocate the previous month's surplus
        // If it's not allocated by start of the next month, it will be automatically allocated to savings
        YearMonth expiredSurplusMonth = YearMonth.now().minusMonths(2);
        if (!family.getMonthlySurpluses().containsKey(expiredSurplusMonth))
            return;

        MonthlySurplus expiredSurplus = family.getMonthlySurpluses().get(expiredSurplusMonth);
        int expiredSurplusAmount = expiredSurplus.getSurplus();
        if (expiredSurplusAmount <= 0) {
            return;
        }

        family.addToSavings(expiredSurplusAmount);
    }

}
