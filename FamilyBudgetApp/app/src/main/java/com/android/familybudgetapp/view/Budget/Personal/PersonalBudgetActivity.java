package com.android.familybudgetapp.view.Budget.Personal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.BudgetRecyclerViewAdapter;
import com.android.familybudgetapp.view.HomePage.HomePageActivity;

import java.util.List;

public class PersonalBudgetActivity extends AppCompatActivity implements PersonalBudgetView,
    BudgetRecyclerViewAdapter.ItemSelectionListener{

    private Button btnBudgetDetails;

    protected void onCreate(Bundle savedInstanceSate)
    {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_show_budget_personal);
        final PersonalBudgetPresenter presenter = new PersonalBudgetPresenter();
        findViewById(R.id.btn_personal_budget).setOnClickListener(v -> showDetails());

        PersonalBudgetViewModel viewModel = new ViewModelProvider(this).get(PersonalBudgetViewModel.class);
        viewModel.getPresenter().setView(this);

        // recycler
        List<Tuples<String,Integer>> expensesList = viewModel.getPresenter().getExpensePerCategory(viewModel.getPresenter().getExpensesOfCurrentUser());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BudgetRecyclerViewAdapter(expensesList, this)); // they had ,this
    }

    protected void onPause()
    {
        super.onPause();
    }

    // used on button
    private void showDetails()
    {
    }


    /**
     * Default action for the button of every object of the recycler
     */
    public void DefaultAction_ReturnToHome()
    {
        Intent intent = new Intent(PersonalBudgetActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}
