package com.android.familybudgetapp.view.budget.detailedBudget;

import androidx.lifecycle.SavedStateHandle;

public interface DetailedBudgetView {

    /**
     * Retrieves the current {@link SavedStateHandle} from the associated ViewModel.
     *
     * @return the SavedStateHandle instance managed by the DetailedBudgetViewModel,
     *         allowing access to saved state and inter-component data sharing.
     */
    SavedStateHandle getState();
}
