package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;

import com.android.familybudgetapp.view.base.BaseView;

public interface CashFlowCategoryCreateView extends BaseView
{
    void clearFields();

    void showAddCategoryMsg(String title, String message);
}
