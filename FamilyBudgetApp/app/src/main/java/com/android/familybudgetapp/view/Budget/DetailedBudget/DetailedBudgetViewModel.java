package com.android.familybudgetapp.view.Budget.DetailedBudget;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.Budget.CashFlowManager.monthlyManager;
import com.android.familybudgetapp.view.Budget.CashFlowManager.yearlyManager;
import com.android.familybudgetapp.view.Budget.ShowBudget.cashFlowType;

import java.util.ArrayList;
import java.util.Collections;

public class DetailedBudgetViewModel extends ViewModel {
    private DetailedBudgetPresenter presenter;
    private SavedStateHandle state;

    DetailedBudgetViewModel(){
        presenter = new DetailedBudgetPresenter();
        UserDAO userDAO = new UserDAOMemory();
        presenter.setUserDao(userDAO);
        presenter.setCurrentUser(userDAO.findByID(Initializer.currentUserID));
    }

    public DetailedBudgetPresenter getPresenter() {
        return presenter;
    }

    public DetailedBudgetViewModel(SavedStateHandle savedStateHandle){
        this();
        state = savedStateHandle;
        if (state.contains("dateRange"))
        {
            if (state.get("dateRange").equals("Monthly"))
                presenter.setCashFlowManager(new monthlyManager());
            else
                presenter.setCashFlowManager(new yearlyManager());
        }
        else
            throw new IllegalStateException("dateRange was not given with Intent");

        if (state.contains("viewGroup"))
        {
            if (state.get("viewGroup").equals("Personal"))
                presenter.getCashFlowManager().setUsers(new ArrayList<>(Collections.singletonList(presenter.getCurrentUser())));
            else
                presenter.getCashFlowManager().setUsers(new ArrayList<>(presenter.getCurrentUser().getFamily().getMembers().values()));
        }
        else
            throw new IllegalStateException("viewGroup was not given with Intent");

        if (state.contains("type"))
        {
            if (state.get("type").equals("Expense"))
                presenter.setType(cashFlowType.Expense);
            else if (state.get("type").equals("Income"))
                presenter.setType(cashFlowType.Income);
            else if (state.get("type").equals("Surplus"))
                presenter.setType(cashFlowType.Both);
        }
        else
            throw new IllegalStateException("viewGroup was not given with Intent");
    }

    public SavedStateHandle getState()
    {
        return state;
    }


}
