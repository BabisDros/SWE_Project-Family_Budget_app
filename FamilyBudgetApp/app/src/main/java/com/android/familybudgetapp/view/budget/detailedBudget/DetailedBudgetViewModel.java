package com.android.familybudgetapp.view.budget.detailedBudget;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.budget.cashFlowManager.FamilyUserStrategy;
import com.android.familybudgetapp.view.budget.cashFlowManager.PersonalUserStrategy;
import com.android.familybudgetapp.view.budget.cashFlowManager.MonthlyManager;
import com.android.familybudgetapp.view.budget.cashFlowManager.YearlyManager;
import com.android.familybudgetapp.view.budget.showBudget.cashFlowType;

public class DetailedBudgetViewModel extends ViewModel {
    private DetailedBudgetPresenter presenter;
    private SavedStateHandle state;

    DetailedBudgetViewModel(){
        presenter = new DetailedBudgetPresenter();
        UserDAO userDAO = new UserDAOMemory();
        presenter.setUserDao(userDAO);
        presenter.setCurrentUser(userDAO.findByID(Initializer.currentUserID));
    }

    /**
     * Retrieves the instance of the DetailedBudgetPresenter associated with this ViewModel.
     *
     * @return the DetailedBudgetPresenter instance used for managing budget details and interactions.
     */
    public DetailedBudgetPresenter getPresenter() {
        return presenter;
    }

    public DetailedBudgetViewModel(SavedStateHandle savedStateHandle){
        this();
        state = savedStateHandle;
        if (state.contains("dateRange"))
        {
            if (state.get("dateRange").equals("Monthly"))
                presenter.setCashFlowManager(new MonthlyManager());
            else
                presenter.setCashFlowManager(new YearlyManager());
        }
        else
            throw new IllegalStateException("dateRange was not given with Intent");

        if (state.contains("viewGroup"))
        {
            if (state.get("viewGroup").equals("Personal"))
                presenter.getCashFlowManager().setUserRetrievalStrategy(new PersonalUserStrategy(presenter.getCurrentUser()));
            else
                presenter.getCashFlowManager().setUserRetrievalStrategy(new FamilyUserStrategy(presenter.getCurrentUser().getFamily()));
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

    /**
     * Retrieves the SavedStateHandle associated with this ViewModel.
     *
     * @return the SavedStateHandle instance used to store and manage UI-related and stateful data.
     */
    public SavedStateHandle getState()
    {
        return state;
    }


}
