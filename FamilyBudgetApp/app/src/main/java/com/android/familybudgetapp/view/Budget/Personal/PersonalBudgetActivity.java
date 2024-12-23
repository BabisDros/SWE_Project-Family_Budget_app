package com.android.familybudgetapp.view.Budget.Personal;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.familybudgetapp.R;

public class PersonalBudgetActivity extends AppCompatActivity implements PersonalBudgetView{

    private Button btnBudgetDetails;

    protected void onCreate(Bundle savedInstanceSate)
    {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_show_budget);
        final PersonalBudgetPresenter presenter = new PersonalBudgetPresenter();
        findViewById(R.id.btn_personal_budget).setOnClickListener(v -> showDetails());
    }

    protected void onPause()
    {
        super.onPause();
    }

    private void showDetails()
    {

    }
}
