package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.ViewModelProvider;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.base.BaseActivity;

public class CashFlowCategoryCreateActivity extends BaseActivity<CashFlowCategoryCreateViewModel>
        implements CashFlowCategoryCreateView, AdapterView.OnItemSelectedListener
{
    final String INCOME = "Income";
    final String EXPENSE = "Expense";
    String[] cashFlowCategories = {INCOME, EXPENSE};
    Button btnSave;
    EditText limitField, nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashflow_category_management);
        viewModel.getPresenter().setView(this);

        limitField = findViewById(R.id.limit_field);
        nameField = findViewById(R.id.name_field);
        setUpSpinner();
        setupBtnSave();
    }

    @Override
    protected CashFlowCategoryCreateViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(CashFlowCategoryCreateViewModel.class);
    }

    private String getLimit()
    {
        return limitField.getText().toString();
    }

    private String getName()
    {
        return nameField.getText().toString().trim();
    }

    private void setupBtnSave()
    {
        btnSave = findViewById(R.id.btn_save);

    }

    private void setUpSpinner()
    {
        Spinner categorySpinner = findViewById(R.id.spinner_categories);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        cashFlowCategories
                );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (cashFlowCategories[position].equals(EXPENSE))
        {
            limitField.setVisibility(View.VISIBLE);
        }
        else
        {
            limitField.setVisibility(View.GONE);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }
}
