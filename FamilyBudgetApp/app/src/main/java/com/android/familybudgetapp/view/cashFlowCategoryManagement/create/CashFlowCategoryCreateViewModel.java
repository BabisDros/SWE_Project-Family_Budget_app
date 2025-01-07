package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;


import com.android.familybudgetapp.view.base.BaseViewModel;

public class CashFlowCategoryCreateViewModel extends BaseViewModel<CashFlowCategoryCreatePresenter>
{
    @Override
    protected CashFlowCategoryCreatePresenter createPresenter()
    {
        return new CashFlowCategoryCreatePresenter();
    }
}
