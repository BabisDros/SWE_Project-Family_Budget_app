package com.android.familybudgetapp.view.cashFlowCategoryManagement.edit;

import com.android.familybudgetapp.view.base.BaseView;

public interface CashFlowCategoryEditView extends BaseView
{
    void setNameField(String name);

    void setLimitField(String limit);

    void goToOverview();

    void setSpinnerToExpense();
}
