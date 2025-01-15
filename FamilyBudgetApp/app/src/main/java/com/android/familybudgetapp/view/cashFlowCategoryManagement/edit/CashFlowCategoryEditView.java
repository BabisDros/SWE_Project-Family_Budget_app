package com.android.familybudgetapp.view.cashFlowCategoryManagement.edit;

import com.android.familybudgetapp.view.base.BaseView;

public interface CashFlowCategoryEditView extends BaseView
{
    /**
     * Sets the name field with the provided value.
     *
     * @param name The name to be set in the name field.
     */
    void setNameField(String name);

    /**
     * Sets the limit field with the provided value.
     *
     * @param limit The limit to be set in the limit field.
     */
    void setLimitField(String limit);


    /**
     * Changes the Activity to cashFlowCategoryOverviewActivity.
     */
    void goToCashFlowCategoryOverview();

    /**
     * Sets the spinner to the "Expense" mode.
     */
    void setSpinnerToExpense();
}
