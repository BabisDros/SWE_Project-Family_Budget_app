package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;

import com.android.familybudgetapp.view.base.BaseView;

public interface CashFlowCategoryCreateView extends BaseView
{
    /**
     * Clears all fields.
     */
    void clearFields();

    /**
     * Shows a message with a title and specific message.
     *
     * @param title The title of the message.
     * @param message The message of the message.
     */
    void showAddCategoryMsg(String title, String message);
}
