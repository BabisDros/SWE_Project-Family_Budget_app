package com.android.familybudgetapp.view.Budget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.HomePage.HomePageActivity;

import java.util.List;

public class BudgetActivity extends AppCompatActivity implements BudgetView {
    private BudgetViewModel vm;

    protected void onCreate(Bundle savedInstanceSate)
    {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_show_budget_personal);
        final BudgetPresenter presenter = new BudgetPresenter();
        findViewById(R.id.btn_personal_expenses).setOnClickListener(v -> showDetailedExpenses());
        findViewById(R.id.btn_personal_income).setOnClickListener(v -> showDetailedIncome());
        findViewById(R.id.btn_date_range).setOnClickListener(v -> changeBudgetDateRange());
        vm = new ViewModelProvider(this).get(BudgetViewModel.class);
        vm.getPresenter().setView(this);

        Intent intent = getIntent();
        String title = "";
        if (intent != null) { // else is default state
            if (intent.hasExtra("viewGroup")) {
                String group = intent.getStringExtra("viewGroup");
                vm.getState().set("viewGroup", group);
                title += group + " ";
            }
            else
                title += "Personal ";
            if (intent.hasExtra("dateRange")) { //The first time they are false
                String dateRange = intent.getStringExtra("dateRange");
                vm.getState().set("dateRange", dateRange);
                ((TextView) findViewById(R.id.btn_date_range)).setText(vm.getNextDateRange() + " info");
                title += dateRange + " ";
            }
            else
                title += "Monthly ";
        }
        title += "Budget";
        ((TextView)findViewById(R.id.budget_title_personal_text)).setText(title);

        // recycler for expense, use different function based on state
        List<Tuples<String,Integer>> expensesList = getExpensePerCategory();
        RecyclerView recyclerViewExpense = findViewById(R.id.recyclerView_expense);
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

    }

    private void showDetailedIncome()
    {

    }

    private void setSurplus()
    {
        setSurplus(vm.getPresenter().calculateSurplus());
    }
    @Override
    public void setSurplus(int amount) {
        String val = vm.getState().get("dateRange");
        ((TextView)findViewById(R.id.txt_personal_monthly_surplus)).setText("Surplus: " + String.valueOf(amount) + val);
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

    /**
     * Default action for onClick listener
     */
    public void DefaultAction_ReturnToHome()
    {
        Intent intent = new Intent(BudgetActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}
