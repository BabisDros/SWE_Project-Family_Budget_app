package com.android.familybudgetapp.view.cashFlowCategoryManagement;


import com.android.familybudgetapp.view.base.BaseViewModel;

public class CashFlowCategoryViewModel extends BaseViewModel<CashFlowCategoryPresenter>
{
    @Override
    protected CashFlowCategoryPresenter createPresenter()
    {
        return new CashFlowCategoryPresenter();
    }
}
