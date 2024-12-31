package com.android.familybudgetapp.view.GlobalStatistics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.utilities.Quadruples;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.ShowBudget.BudgetRecyclerViewAdapter;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class GlobalStatisticsActivity extends AppCompatActivity implements GlobalStatisticsView {

    private GlobalStatisticsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_statistics);
        presenter = new GlobalStatisticsPresenter(new FamilyDAOMemory());
        findViewById(R.id.btn_back).setOnClickListener(v -> goBack());

        // recycler for expense
        List<Tuples<YearMonth, Double>> monthlyList = getMonthlyStat();
        RecyclerView recyclerViewMonthly = findViewById(R.id.recyclerView_monthly);
        recyclerViewMonthly.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMonthly.setAdapter(new GlobalStatisticsMonthlyViewAdapter(monthlyList));

        // recycler for expense
        List<Quadruples<YearMonth, Double, Double, Double>> yearlyList = getYearlyStat();
        RecyclerView recyclerViewYearly = findViewById(R.id.recyclerView_yearly);
        recyclerViewYearly.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewYearly.setAdapter(new GlobalStatisticsYearlyViewAdapter(yearlyList));

    }

    private void goBack() {
        this.finish();
    }

    public List<Tuples<YearMonth, Double>> getMonthlyStat()
    {
        return presenter.getMonthlyStat();
    }

    public List<Quadruples<YearMonth, Double, Double, Double>> getYearlyStat()
    {
        return presenter.getYearlyStat();
    }

}
