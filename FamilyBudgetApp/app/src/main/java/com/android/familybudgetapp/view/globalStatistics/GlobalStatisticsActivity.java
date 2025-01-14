package com.android.familybudgetapp.view.globalStatistics;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.utilities.Quadruples;
import com.android.familybudgetapp.utilities.Tuples;

import java.time.YearMonth;
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

    /**
     * Navigates back to the previous activity and finishes the current activity.
     */
    private void goBack() {
        this.finish();
    }

    protected void onPause()
    {
        super.onPause();
    }

    /**
     * Retrieves a list of statistical data on a monthly basis.
     * Each entry in the list contains a YearMonth instance and a corresponding statistic value.
     *
     * @return a list of tuples where each tuple consists of a YearMonth object and a Double
     *         representing the statistic for that month.
     */
    public List<Tuples<YearMonth, Double>> getMonthlyStat()
    {
        return presenter.getMonthlyStat();
    }

    /**
     * Retrieves a list of statistical data on a yearly basis.
     * Each entry in the list contains a YearMonth instance and three corresponding statistical values.
     *
     * @return a list of quadruples where each quadruple consists of a YearMonth object and three Double
     *         values representing different statistics for the given year and month.
     */
    public List<Quadruples<YearMonth, Double, Double, Double>> getYearlyStat()
    {
        return presenter.getYearlyStat();
    }

}
