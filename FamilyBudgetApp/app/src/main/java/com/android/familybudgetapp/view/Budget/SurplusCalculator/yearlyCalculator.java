package com.android.familybudgetapp.view.Budget.SurplusCalculator;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.InDateRange;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.cashFlowType;

import java.util.List;

public class yearlyCalculator extends CashFlowCalculator {
    List<User> users;

    @Override
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    protected List<User> getUsers()
    {
        return users;
    }

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
        return cashFlow.getYearlyAmount();
    }
}
