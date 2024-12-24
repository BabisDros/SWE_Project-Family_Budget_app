package com.android.familybudgetapp.view.HomePage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.view.Budget.Personal.PersonalBudgetActivity;

public class HomePageActivity extends AppCompatActivity implements HomePageView {

    private static boolean initialized = false;

    /**
     * Current app homepage
     * Will be used as the starting point to go from activity to activity
     * Add activity buttons as needed
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_home);

        final HomePagePresenter presenter = new HomePagePresenter(this);

        findViewById(R.id.btn_family_budget).setOnClickListener(v -> presenter.onFamilyBudget());

        findViewById(R.id.btn_personal_budget).setOnClickListener(v -> presenter.onPersonalBudget());

        if (!initialized)
        {
            new MemoryInitializer().prepareData();
            initialized = true;
        }
    }

    public void personalBudget()
    {
        Intent intent = new Intent(HomePageActivity.this, PersonalBudgetActivity.class);
        startActivity(intent);
    }

    public void familyBudget()
    {
    //    Intent intent = new Intent(HomePageActivity.this, FamilyBudgetActivity.class);
    //    startActivity(intent);
    }
}
