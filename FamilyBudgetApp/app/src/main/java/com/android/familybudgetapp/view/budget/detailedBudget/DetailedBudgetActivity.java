package com.android.familybudgetapp.view.budget.detailedBudget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.utilities.Quadruples;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link DetailedBudgetActivity} class shows more details (e.g the dates for each
 * {@link com.android.familybudgetapp.domain.CashFlow CashFlow}) for the specified category
 * (either {@link com.android.familybudgetapp.domain.Expense Expense} or {@link com.android.familybudgetapp.domain.Income Income})
 * for the given date range (either {@code Monthly} or {@code Yearly}.
 * On detailed Surplus, it shows the contribution of each {@link com.android.familybudgetapp.domain.User User}
 * on the current viewpage.
 */
public class DetailedBudgetActivity extends AppCompatActivity implements DetailedBudgetView{
    private DetailedBudgetViewModel vm;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detailed_budget);
        findViewById(R.id.btn_back).setOnClickListener(v -> goBack());

        vm = new ViewModelProvider(this).get(DetailedBudgetViewModel.class);
        vm.getPresenter().setView(this);

        Intent intent = getIntent();
        String title = "Detailed ";
        if (intent != null) {
            if (intent.hasExtra("viewGroup")) {
                String group = intent.getStringExtra("viewGroup");
                title += group + " ";
            }
            if (intent.hasExtra("dateRange")) {
                String dateRange = intent.getStringExtra("dateRange");
                title += dateRange + " ";
            }
            if (intent.hasExtra("type")) {
                String type = intent.getStringExtra("type");
                title += type;
            }
        }

        ((TextView)findViewById(R.id.text_title)).setText(title);

        List<Quadruples<String, String, Integer, List<LocalDateTime>>> cashFlows = getCashFlows();
        RecyclerView recyclerView = findViewById(R.id.recyclerView_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DetailedBudgetRecyclerViewAdapter(cashFlows));
    }

    private void goBack() {
        this.finish();
    }

    protected void onPause()
    {
        super.onPause();
    }

    /**
     * <pre>
     * List of {@link Quadruples} which correspond to:
     * Category of CashFlow
     * Name of user who added it
     * Amount of CashFlow for the wanted range
     * DateStart and DateEnd (if it exists)</pre>
     * @return CashFlow and owner data in a format the Recycler can make use of
     */
    private List<Quadruples<String, String, Integer, List<LocalDateTime>>> getCashFlows()
    {
        return vm.getPresenter().getFormatedCashFlows();
    }

    /**
     * Retrieves the current {@link SavedStateHandle} from the associated ViewModel.
     *
     * @return the SavedStateHandle instance managed by the DetailedBudgetViewModel,
     *         allowing access to saved state and inter-component data sharing.
     */
    @Override
    public SavedStateHandle getState() {
        return vm.getState();
    }
}
