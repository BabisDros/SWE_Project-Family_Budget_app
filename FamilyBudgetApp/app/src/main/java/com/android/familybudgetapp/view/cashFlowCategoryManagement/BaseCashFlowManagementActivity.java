package com.android.familybudgetapp.view.cashFlowCategoryManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.ViewModel;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.base.BaseActivity;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.overview.CashFlowCategoryOverviewActivity;

public abstract class BaseCashFlowManagementActivity<V extends ViewModel> extends BaseActivity<V>
        implements AdapterView.OnItemSelectedListener
{
    public static final String INCOME = "Income";
    public static final String EXPENSE = "Expense";
    protected String[] cashFlowCategories = {INCOME, EXPENSE};
    protected Button btnSave;
    protected EditText limitField, nameField;
    protected Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashflow_category_management);

        limitField = findViewById(R.id.limit_field);
        nameField = findViewById(R.id.name_field);
        setupSpinner();
        setupBtnSave();
    }

    protected String getLimit()
    {
        return limitField.getText().toString().trim();
    }

    protected String getName()
    {
        return nameField.getText().toString().trim();
    }

    protected void setupBtnSave()
    {
        btnSave = findViewById(R.id.btn_saveCategory);
        btnSave.setOnClickListener(v -> buttonSaveClicked(getName(), getLimit()));
    }

    protected abstract void buttonSaveClicked(String name, String limit);

    protected void setupSpinner()
    {
        categorySpinner = findViewById(R.id.spinner_categories);
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
        setType(cashFlowCategories[position]);
    }

    protected abstract void setType(String type);

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }


    public void goToOverview()
    {
        Intent intent = new Intent(this, CashFlowCategoryOverviewActivity.class);
        startActivity(intent);
        finish();
    }
}
