package com.android.familybudgetapp.view.budget.showBudget;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.budget.cashFlowManager.FamilyUserStrategy;
import com.android.familybudgetapp.view.budget.cashFlowManager.PersonalUserStrategy;
import com.android.familybudgetapp.view.budget.cashFlowManager.MonthlyManager;
import com.android.familybudgetapp.view.budget.cashFlowManager.YearlyManager;

import java.util.Objects;

public class BudgetViewModel extends ViewModel {
    private BudgetPresenter presenter;
    private SavedStateHandle state;

    public BudgetViewModel()
    {
        presenter = new BudgetPresenter();
        UserDAO userDAO = new UserDAOMemory();
        presenter.setUserDao(userDAO);
        presenter.setCurrentUser(userDAO.findByID(Initializer.currentUserID));
    }

    public BudgetPresenter getPresenter() {
        return presenter;
    }

    public BudgetViewModel(SavedStateHandle savedStateHandle) {
        this();
        state = savedStateHandle;
        presenter.setFamilyMonthlySurplusManager(new MonthlyManager());
        presenter.getFamilyMonthlySurplusManager().setUserRetrievalStrategy(
                new FamilyUserStrategy(presenter.getCurrentUser().getFamily()));

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
                presenter.getCashFlowManager().setUserRetrievalStrategy(
                        new PersonalUserStrategy(presenter.getCurrentUser()));
            else
                presenter.getCashFlowManager().setUserRetrievalStrategy(
                        new FamilyUserStrategy(presenter.getCurrentUser().getFamily()));
        }
        else
            throw new IllegalStateException("viewGroup was not given with Intent");

    }

    public SavedStateHandle getState()
    {
        return state;
    }

    /**
     * Called on new activity intent to update the dateRange Extra
     */
    public void changeToNextDateRange()
    {
        state.set("dateRange", getNextDateRange());
    }

    public String getNextDateRange()
    {
        switch ((String) Objects.requireNonNull(state.get("dateRange")))
        {
            case "Monthly":
                return "Yearly";
            case "Yearly":
                return "Monthly";
        }
        throw new IllegalArgumentException((String) Objects.requireNonNull(state.get("dateRange")) + "is not acceptable");
    }

    /**
     * Called on new activity intent to update the dateRange Extra
     */
    public void changeToNextViewGroup()
    {
        state.set("viewGroup", getNextViewGroup());
    }

    public String getNextViewGroup()
    {
        switch ((String) Objects.requireNonNull(state.get("viewGroup")))
        {
            case "Personal":
                return "Family";
            case "Family":
                return "Personal";
        }
        throw new IllegalArgumentException((String) Objects.requireNonNull(state.get("viewGroup")) + "is not acceptable");
    }

    protected void onCleared()
    {
        super.onCleared();
    }
}
