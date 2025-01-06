package com.android.familybudgetapp.view.cashFlowCategoryManagement;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.android.familybudgetapp.view.base.BaseActivity;

public class CashFlowCategoryActivity extends BaseActivity<CashFlowCategoryViewModel> implements CashFlowCategoryView
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewModel.getPresenter().setView(this);


    }

    @Override
    protected CashFlowCategoryViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(CashFlowCategoryViewModel.class);
    }
}
