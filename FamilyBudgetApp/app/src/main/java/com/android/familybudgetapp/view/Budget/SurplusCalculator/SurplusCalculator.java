package com.android.familybudgetapp.view.Budget.SurplusCalculator;

import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.ShowBudget.cashFlowType;

import java.util.List;


public interface SurplusCalculator {

    int CalculateSurplus();

    void setUsers(List<User> users);

    List<Tuples<String, Integer>> CalculateAmountPerCategory(cashFlowType type);

}
