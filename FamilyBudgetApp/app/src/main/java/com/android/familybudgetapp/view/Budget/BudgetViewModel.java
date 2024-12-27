package com.android.familybudgetapp.view.Budget;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.Budget.SurplusCalculator.monthlyCalculator;
import com.android.familybudgetapp.view.Budget.SurplusCalculator.yearlyCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class BudgetViewModel extends ViewModel {
    private BudgetPresenter presenter;
    private SavedStateHandle state;

    public BudgetViewModel()
    {
        presenter = new BudgetPresenter();
        UserDAO userDAO = new UserDAOMemory();
        presenter.setUserDao(userDAO);
        presenter.setCurrentUser(userDAO.find(Initializer.currentUserID));
    }

    public BudgetPresenter getPresenter() {
        return presenter;
    }

    public BudgetViewModel(SavedStateHandle savedStateHandle) {
        this();
        state = savedStateHandle;
        if (!state.contains("dateRange")) // default
            state.set("dateRange", "Monthly");
        if (!state.contains("viewGroup"))
            state.set("viewGroup", "Personal");

        if (state.get("dateRange").equals("Monthly"))
            presenter.setSurplusCalculator(new monthlyCalculator());
        else
            presenter.setSurplusCalculator(new yearlyCalculator());

        if (state.get("viewGroup").equals("Personal"))
            presenter.getSurplusCalculator().setUsers(new ArrayList<>(Collections.singletonList(presenter.getCurrentUser())));
        else
            presenter.getSurplusCalculator().setUsers(new ArrayList<>(presenter.getCurrentUser().getFamily().getMembers().values()));

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

    protected void onCleared()
    {
        super.onCleared();
    }
}
