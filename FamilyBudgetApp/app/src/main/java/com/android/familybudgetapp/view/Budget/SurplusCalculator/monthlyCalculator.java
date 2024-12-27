package com.android.familybudgetapp.view.Budget.SurplusCalculator;

import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.InDateRange;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.cashFlowType;

import java.util.ArrayList;
import java.util.List;

public class monthlyCalculator extends CashFlowCalculator {
    private List<User> users;

    @Override
    public void setUsers(List<User> users)
    {
        this.users = new ArrayList<>(users);
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

    protected boolean inDateRange(CashFlow cashFlow)
    {
        return InDateRange.cashFlowInMonthlyRange(cashFlow);
    }

    @Override
    protected int getAmountForRange(CashFlow cashFlow) {
        return cashFlow.getMonthlyAmount();
    }
}
