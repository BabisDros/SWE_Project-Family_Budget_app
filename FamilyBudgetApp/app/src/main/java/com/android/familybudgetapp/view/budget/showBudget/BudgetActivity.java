package com.android.familybudgetapp.view.budget.showBudget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.utilities.AmountConversion;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.budget.detailedBudget.DetailedBudgetActivity;
import com.android.familybudgetapp.view.homePage.HomePageActivity;
import com.android.familybudgetapp.view.base.BaseActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class BudgetActivity extends BaseActivity<BudgetViewModel> implements BudgetView {
    private BudgetViewModel vm;

    @Override
    protected BudgetViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(BudgetViewModel.class);
    }

    protected void onCreate(Bundle savedInstanceSate)
    {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_show_budget);
        findViewById(R.id.btn_detailed_expenses).setOnClickListener(v -> showDetailedExpenses());
        findViewById(R.id.btn_detailed_income).setOnClickListener(v -> showDetailedIncome());
        findViewById(R.id.btn_detailed_surplus).setOnClickListener(v -> showDetailedSurplus());
        findViewById(R.id.btn_date_range).setOnClickListener(v -> changeBudgetDateRange());
        findViewById(R.id.btn_view_group).setOnClickListener(v  -> changeBudgetViewGroup());
        findViewById(R.id.btn_back).setOnClickListener(v -> goBack());
        findViewById(R.id.add_cashflow).setOnClickListener(v -> addNewCashFlow());
        vm = new ViewModelProvider(this).get(BudgetViewModel.class);
        vm.getPresenter().setView(this);

        Intent intent = getIntent();
        String title = "";
        if (intent != null) {
            if (intent.hasExtra("viewGroup")) {
                String group = intent.getStringExtra("viewGroup");
                ((TextView) findViewById(R.id.btn_view_group)).setText(vm.getNextViewGroup() + " info");
                title += group + " ";
            }
            if (intent.hasExtra("dateRange")) {
                String dateRange = intent.getStringExtra("dateRange");
                ((TextView) findViewById(R.id.btn_date_range)).setText(vm.getNextDateRange() + " info");
                title += dateRange + " ";
            }
        }
        title += "Budget";
        ((TextView)findViewById(R.id.budget_title_personal_text)).setText(title);

        // recycler for expense
        List<Tuples<String,Integer>> expensesList = getExpensePerCategory();
        RecyclerView recyclerViewExpense = findViewById(R.id.recyclerView_expenses);
        recyclerViewExpense.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExpense.setAdapter(new BudgetRecyclerViewAdapter(expensesList));

        // recycler for income
        List<Tuples<String,Integer>> incomeList = getIncomePerCategory();
        RecyclerView recyclerViewIncome = findViewById(R.id.recyclerView_income);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewIncome.setAdapter(new BudgetRecyclerViewAdapter(incomeList));

        //surplus
        setSurplus();

    }

    protected void onPause()
    {
        super.onPause();
    }

    private void goBack() {
        this.finish();
    }

    private List<Tuples<String,Integer>> getExpensePerCategory()
    {
        return vm.getPresenter().getExpensePerCategory();
    }

    private List<Tuples<String,Integer>> getIncomePerCategory()
    {
        return vm.getPresenter().getIncomePerCategory();
    }

    private void showDetailedExpenses()
    {
        Intent intent = new Intent(BudgetActivity.this, DetailedBudgetActivity.class);
        intent.putExtra("dateRange", (String) vm.getState().get("dateRange"));
        intent.putExtra("viewGroup", (String) vm.getState().get("viewGroup"));
        intent.putExtra("type", "Expense");

        startActivity(intent);
    }

    private void showDetailedIncome()
    {
        Intent intent = new Intent(BudgetActivity.this, DetailedBudgetActivity.class);
        intent.putExtra("dateRange", (String) vm.getState().get("dateRange"));
        intent.putExtra("viewGroup", (String) vm.getState().get("viewGroup"));
        intent.putExtra("type", "Income");

        startActivity(intent);
    }

    private void showDetailedSurplus()
    {
        Intent intent = new Intent(BudgetActivity.this, DetailedBudgetActivity.class);
        intent.putExtra("dateRange", (String) vm.getState().get("dateRange"));
        intent.putExtra("viewGroup", (String) vm.getState().get("viewGroup"));
        intent.putExtra("type", "Surplus");

        startActivity(intent);
    }

    private void setSurplus()
    {
        int surplus = vm.getPresenter().calculateSurplus();
        setSurplus(surplus);
    }
    @Override
    public void setSurplus(int amount) {
        ((TextView)findViewById(R.id.txt_personal_monthly_surplus)).setText("Surplus: " + AmountConversion.toEuro(amount));
    }

    private void changeBudgetDateRange()
    {
        vm.changeToNextDateRange();
        Intent intent = new Intent(BudgetActivity.this, BudgetActivity.class);
        intent.putExtra("dateRange", (String) vm.getState().get("dateRange"));
        intent.putExtra("viewGroup", (String) vm.getState().get("viewGroup"));

        startActivity(intent);
        this.finish();
    }

    private void changeBudgetViewGroup()
    {
        vm.changeToNextViewGroup();
        Intent intent = new Intent(BudgetActivity.this, BudgetActivity.class);
        intent.putExtra("dateRange", (String) vm.getState().get("dateRange"));
        intent.putExtra("viewGroup", (String) vm.getState().get("viewGroup"));

        startActivity(intent);
        this.finish();
    }

    private void addNewCashFlow() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_add_cashflow, null);

        Map<String, CashFlowCategory> categories = vm.getPresenter().getCurrentUser().getFamily().getCashFlowCategories();
        List<String> categoryNames = new ArrayList<>(categories.keySet());
        Spinner categorySpinner = bottomSheetView.findViewById(R.id.category_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        final EditText cashFlowAmountInput = bottomSheetView.findViewById(R.id.cashflow_value);
        final CheckBox isRecurring = bottomSheetView.findViewById(R.id.checkbox_is_recurring);
        final Spinner recurrenceSpinner = bottomSheetView.findViewById(R.id.recurrence_spinner);
        final LinearLayout recurrencePeriodPicker = bottomSheetView.findViewById(R.id.recurrence_period_picker);
        final LinearLayout dateEndPicker = bottomSheetView.findViewById(R.id.date_end_picker);

        // Show or hide recurring date pickers based on checkbox selection
        isRecurring.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dateEndPicker.setVisibility(View.VISIBLE);
                recurrencePeriodPicker.setVisibility(View.VISIBLE);
            } else {
                dateEndPicker.setVisibility(View.GONE);
                recurrencePeriodPicker.setVisibility(View.GONE);
            }
        });

        // Date Selectors
        final Button startDateButton = bottomSheetView.findViewById(R.id.btn_start_date);
        startDateButton.setOnClickListener(v -> {
            showDatePicker(startDateButton);
        });
        final Button endDateButton = bottomSheetView.findViewById(R.id.btn_end_date);
        endDateButton.setOnClickListener(v -> {
            showDatePicker(endDateButton);
        });

        // Submit button to handle the data
        Button submitButton = bottomSheetView.findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(v -> {

            String cashFlowAmount = cashFlowAmountInput.getText().toString();

            // Parse startDate
            LocalDateTime dateStart = null;
            String dateStartStr = startDateButton.getText().toString();
            if (!dateStartStr.equals(getString(R.string.select_start_date))) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                dateStart = LocalDateTime.parse(startDateButton.getText().toString() + " 00:00", formatter);
            }

            // Parse endDate
            LocalDateTime dateEnd = null;
            String dateEndStr = endDateButton.getText().toString();
            if (!dateEndStr.equals(getString(R.string.select_end_date)) && isRecurring.isChecked()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                dateEnd = LocalDateTime.parse(endDateButton.getText().toString() + " 00:00", formatter);
            }

            int recurPeriodIdx = recurrenceSpinner.getSelectedItemPosition();
            String categoryName = (String) categorySpinner.getSelectedItem();

            // Validate and add cash flow with presenter
            vm.getPresenter().addCashFlow(categoryName, cashFlowAmount,
                    isRecurring.isChecked(), dateStart, dateEnd, recurPeriodIdx);

            refreshData();
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void showDatePicker(Button buttonToUpdate) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            // Format the selected date as "day-month-year"
            String selectedDate = String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year);

            // Update the button text with the selected date
            buttonToUpdate.setText(selectedDate);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void refreshData() {
        // Update expenses list
        List<Tuples<String, Integer>> updatedExpenses = getExpensePerCategory();
        RecyclerView recyclerViewExpense = findViewById(R.id.recyclerView_expenses);
        BudgetRecyclerViewAdapter expenseAdapter = (BudgetRecyclerViewAdapter) recyclerViewExpense.getAdapter();
        if (expenseAdapter != null) {
            expenseAdapter.updateData(updatedExpenses);
        }

        // Update income list
        List<Tuples<String, Integer>> updatedIncome = getIncomePerCategory();
        RecyclerView recyclerViewIncome = findViewById(R.id.recyclerView_income);
        BudgetRecyclerViewAdapter incomeAdapter = (BudgetRecyclerViewAdapter) recyclerViewIncome.getAdapter();
        if (incomeAdapter != null) {
            incomeAdapter.updateData(updatedIncome);
        }

        // Update surplus
        setSurplus();
    }

}
