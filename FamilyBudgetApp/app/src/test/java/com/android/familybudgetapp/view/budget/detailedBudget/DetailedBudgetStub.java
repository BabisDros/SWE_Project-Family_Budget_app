package com.android.familybudgetapp.view.budget.detailedBudget;

import androidx.lifecycle.SavedStateHandle;

public class DetailedBudgetStub implements DetailedBudgetView{

    String type;
    SavedStateHandle savedStateHandle;

    DetailedBudgetStub(){
        savedStateHandle = new SavedStateHandle();
    }
    @Override
    public SavedStateHandle getState() {
        return savedStateHandle;
    }

    public String getType(){
        return savedStateHandle.get("type");
    }

    public void setType(String type) {
        savedStateHandle.set("type", type);
    }
}
