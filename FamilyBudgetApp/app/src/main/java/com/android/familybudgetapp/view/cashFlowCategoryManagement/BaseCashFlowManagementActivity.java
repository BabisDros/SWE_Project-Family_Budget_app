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
    protected Button btnCancel;
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
        setUpCancelBtn();
    }

    /**
     * Retrieves the limit entered by the user.
     *
     * @return A trimmed string of the text in the limit field.
     */
    protected String getLimit()
    {
        return limitField.getText().toString().trim();
    }

    /**
     * Retrieves the name entered by the user.
     *
     * @return A trimmed string of the text in the name field.
     */
    protected String getName()
    {
        return nameField.getText().toString().trim();
    }

    /**
     * Caches the btnSave button and sets it's onClick listener.
     */
    protected void setupBtnSave()
    {
        btnSave = findViewById(R.id.btn_saveCategory);
        btnSave.setOnClickListener(v -> saveClicked(getName(), getLimit()));
    }

    /**
     * Caches the btnCancel button and sets it's onClick listener.
     */
    protected void setUpCancelBtn()
    {
        btnCancel = findViewById(R.id.btn_cancel_edit);
        btnCancel.setOnClickListener(v-> goToCashFlowCategoryOverview());
    }

    /**
     * Abstract method to set the type of the cash flow.
     * Subclasses must implement this method by calling their presenter.
     *
     * @param type The type of cash flow category .
     */
    protected abstract void setType(String type);


    /**
     * Caches the spinner and sets it to selecting cash flow categories.
     * Sets the listener to this object to handle item selection events.
     */
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

    /**
     * Listener to the save button click event.
     * Subclasses must implement this method by calling their presenter.
     *
     * @param name The name of cash flow category .
     * @param limit The limit of cash flow category .
     */
    protected abstract void saveClicked(String name, String limit);

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

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }

    /**
     * Changes Activity to CashFlowCategoryOverviewActivity.
     */
    public void goToCashFlowCategoryOverview()
    {
        Intent intent = new Intent(this, CashFlowCategoryOverviewActivity.class);
        startActivity(intent);
        finish();
    }
}
