package com.android.familybudgetapp.view.Budget.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.BudgetRecyclerViewAdapter;
import com.android.familybudgetapp.view.HomePage.HomePageActivity;

import java.util.List;

public class PersonalBudgetActivity extends AppCompatActivity implements PersonalBudgetView{

    protected void onCreate(Bundle savedInstanceSate)
    {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_show_budget_personal);
        final PersonalBudgetPresenter presenter = new PersonalBudgetPresenter();
        findViewById(R.id.btn_personal_expenses).setOnClickListener(v -> showDetailedExpenses());
        findViewById(R.id.btn_personal_income).setOnClickListener(v -> showDetailedIncome());

        PersonalBudgetViewModel viewModel = new ViewModelProvider(this).get(PersonalBudgetViewModel.class);
        viewModel.getPresenter().setView(this);

        // recycler
        List<Tuples<String,Integer>> expensesList = viewModel.getPresenter().getUserExpensePerCategory();
        RecyclerView recyclerViewExpense = findViewById(R.id.recyclerView_expense);
        recyclerViewExpense.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExpense.setAdapter(new BudgetRecyclerViewAdapter(expensesList));

        // recycler
        List<Tuples<String,Integer>> incomeList = viewModel.getPresenter().getUserIncomePerCategory();
        RecyclerView recyclerViewIncome = findViewById(R.id.recyclerView_income);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewIncome.setAdapter(new BudgetRecyclerViewAdapter(incomeList));

        //surplus
        //((TextView)findViewById(R.id.txt_personal_monthly_surplus)).setText("placeholder");

    }

    protected void onPause()
    {
        super.onPause();
    }

    private void showDetailedExpenses()
    {

    }

    private void showDetailedIncome()
    {

    }


    /**
     * Default action for onClick listener
     */
    public void DefaultAction_ReturnToHome()
    {
        Intent intent = new Intent(PersonalBudgetActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}
