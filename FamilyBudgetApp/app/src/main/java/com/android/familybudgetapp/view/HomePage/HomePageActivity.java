package com.android.familybudgetapp.view.HomePage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.android.familybudgetapp.R;

import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.view.Budget.ShowBudget.BudgetActivity;
import com.android.familybudgetapp.view.GlobalStatistics.GlobalStatisticsActivity;
import com.android.familybudgetapp.view.MoneyBox.ShowMoneyBoxes.ShowMoneyBoxesActivity;
import com.android.familybudgetapp.view.membersManagement.MembersManagementActivity;

import java.util.Objects;

public class HomePageActivity extends AppCompatActivity implements HomePageView {

    public static final String MODE_EXTRA= "Mode";
    /**
     * Current app homepage
     * will be used as the starting point after login/register to go from activity to activity.
     * Add activity buttons as needed.
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

        findViewById(R.id.btn_detailed_expenses).setOnClickListener(v -> presenter.onPersonalBudget());
        findViewById(R.id.btn_moneyboxes).setOnClickListener(v -> presenter.onMoneyBoxes());
        findViewById(R.id.btn_stats).setOnClickListener(v -> presenter.onStats());

        Button btn_usersManagement = findViewById(R.id.btn_usersManagement);
        if (savedInstanceState == null)
        {
            setupUsersManagementBtn(btn_usersManagement);
        }
    }

    private  void setupUsersManagementBtn(Button btn)
    {
        Intent intent = getIntent();

        if(Objects.equals(intent.getStringExtra(HomePageActivity.MODE_EXTRA), FamPos.Member.name()))
        {
            btn.setVisibility(View.GONE);
        }
        else
        {
            btn.setOnClickListener(v->goToMemberManagement());
            btn.setVisibility(View.VISIBLE);
        }
        
    }

    @Override
    public void personalBudget()
    {
        Intent intent = new Intent(HomePageActivity.this, BudgetActivity.class);
        intent.putExtra("dateRange", "Monthly");
        intent.putExtra("viewGroup", "Personal");
        startActivity(intent);
    }

    @Override
    public void moneyBoxes() {
        Intent intent = new Intent(HomePageActivity.this, ShowMoneyBoxesActivity.class);
        startActivity(intent);
    }

    @Override
    public void stats(){
        Intent intent = new Intent(HomePageActivity.this, GlobalStatisticsActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToMemberManagement()
    {
        Intent intent = new Intent(HomePageActivity.this, MembersManagementActivity.class);
        startActivity(intent);
    }

}
