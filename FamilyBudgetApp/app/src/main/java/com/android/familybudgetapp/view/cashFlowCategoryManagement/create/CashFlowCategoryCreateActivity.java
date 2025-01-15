package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.BaseCashFlowManagementActivity;

public class CashFlowCategoryCreateActivity extends BaseCashFlowManagementActivity<CashFlowCategoryCreateViewModel>
        implements CashFlowCategoryCreateView
{
    private AlertDialog.Builder addExtraCategoryDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewModel.getPresenter().setView(this);
        viewModel.getPresenter().setType(cashFlowCategories[0]);

        setupAddCategoryDialog();
    }

    @Override
    protected CashFlowCategoryCreateViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(CashFlowCategoryCreateViewModel.class);
    }

    @Override
    protected void saveClicked(String name, String limit)
    {
        btnSave.setOnClickListener(v -> viewModel.getPresenter().save(getName(), getLimit()));
    }

    /**
     * Calls the presenter to set the type of the cash flow category.
     *
     * @param type The type of the cash flow category to be set.
     */
    public void setType(String type)
    {
        viewModel.getPresenter().setType(type);
    }

    /**
     * Sets up the dialog for adding a new category,
     * the appropriate listeners and names to NegativeButton and setPositiveButton.
     */
    private void setupAddCategoryDialog()
    {
        addExtraCategoryDialog = new AlertDialog.Builder(CashFlowCategoryCreateActivity.this)
                .setCancelable(true)
                .setNegativeButton(R.string.no, (dialog, which) -> categoryDialogNoClicked())
                .setPositiveButton(R.string.yes, (dialog, which) -> categoryDialogYesClicked());
    }

    /**
     * Listener to the NegativeButton click event of Category Dialog.
     * Calls the presenter to navigate to the CashFlowCategoryOverview.
     */
    private void categoryDialogNoClicked()
    {
        goToCashFlowCategoryOverview();
    }

    /**
     * Listener to the PositiveButton click event of Category Dialog.
     * Calls the presenter to reset the input fields.
     */
    private void categoryDialogYesClicked()
    {
        viewModel.getPresenter().resetFields();
    }

    @Override
    public void clearFields()
    {
        viewModel.getPresenter().setType(cashFlowCategories[0]);
        nameField.setText("");
        limitField.setText("");
    }

    @Override
    public void showAddCategoryMsg(String title, String message)
    {
        addExtraCategoryDialog.setTitle(title);
        addExtraCategoryDialog.setMessage(message);
        addExtraCategoryDialog.show();
    }
}
