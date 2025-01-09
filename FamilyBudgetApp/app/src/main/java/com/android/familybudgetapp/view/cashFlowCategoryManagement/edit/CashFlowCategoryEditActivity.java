package com.android.familybudgetapp.view.cashFlowCategoryManagement.edit;

import static com.android.familybudgetapp.view.cashFlowCategoryManagement.overview.CashFlowCategoryOverviewActivity.CASHFLOW_CATEGORY_NAME_EXTRA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.android.familybudgetapp.view.cashFlowCategoryManagement.BaseCashFlowManagementActivity;

public class CashFlowCategoryEditActivity extends BaseCashFlowManagementActivity<CashFlowCategoryEditViewModel>
        implements CashFlowCategoryEditView
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewModel.getPresenter().setView(this);

        Intent intent = getIntent();
        viewModel.getPresenter().setCashFlowCategoryData(intent.getStringExtra(CASHFLOW_CATEGORY_NAME_EXTRA));
    }

    @Override
    protected CashFlowCategoryEditViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(CashFlowCategoryEditViewModel.class);
    }

    @Override
    protected void buttonSaveClicked(String name, String limit)
    {
        btnSave.setOnClickListener(v -> viewModel.getPresenter().save(getName(), getLimit()));
    }

    @Override
    protected void setType(String type)
    {
        viewModel.getPresenter().setType(type);
    }

    @Override
    public void setNameField(String name)
    {
        nameField.setText(name);
    }

    @Override
    public void setLimitField(String limit)
    {
        limitField.setText(limit);
    }

    @Override
    public void setSpinnerToExpense()
    {
        categorySpinner.setSelection(1);
        limitField.setVisibility(View.VISIBLE);
    }
}
